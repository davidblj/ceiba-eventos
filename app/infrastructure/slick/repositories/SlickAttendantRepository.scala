package infrastructure.slick.repositories

import domain.entities.{Attendant, AttendantAssignedResource}
import infrastructure.slick.entities
import infrastructure.slick.entities._
import infrastructure.slick.transformers._
import javax.inject.Inject
import infrastructure.slick.shared.CustomColumnTypes._
import org.joda.time.DateTime
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import infrastructure.slick.shared.CustomTypesTransformers.parse

import scala.concurrent.{ExecutionContext, Future}

class SlickAttendantRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider,
                                          val attendantAssignedResourceRepository: SlickAttendantAssignedResourceRepository,
                                          val resourceRepository: SlickResourceRepository,
                                          val employeeRepository: SlickEmployeeRepository)
                                         (implicit ec: ExecutionContext)
                                         extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._
  val attendantTable = TableQuery[AttendantTable]
  val locationTable = TableQuery[LocationTable]
  val employeeTable = TableQuery[EmployeeTable]
  val assignedResourcesTable = TableQuery[AttendantAssignedResourceTable]

  def add(attendant: Attendant): Future[Int] = {

    def createAttendant(): Future[Attendant] = {

      val attendantTableObject = AttendantTransformer.toTableObject(attendant)
      val query = attendantTable returning attendantTable.map(_.id) += attendantTableObject
      db.run(query).map(id => attendant.setId(id))
    }

    for {
      attendant <- createAttendant()
      _         <- attendantAssignedResourceRepository.add(attendant.assignedResources, attendant.attendantId.get)
    } yield attendant.attendantId.get
  }

  def getAttendantsBy(eventId: Int): Future[List[Attendant]] = {

    def fetchAttendants(): Future[List[(Int, String, Int, String, Int, Int, DateTime)]] = {

      db.run(flat(attendantsQuery()).result)
        .map(attendants => attendants.toList)
    }

    def attendantsQuery(): Query[((AttendantTable, LocationTable), EmployeeTable),
                                 ((entities.Attendant, Location), Employee), Seq] = {

      val attendantsAndLocations = attendantTable join locationTable on (_.location_id === _.id)
      val attendantsAndEmployees = attendantsAndLocations join employeeTable on (_._1.employee_id === _.id)
      attendantsAndEmployees filter { case ((attendant, _), _) => attendant.event_id === eventId }
    }

    def flat(query: Query[((AttendantTable, LocationTable), EmployeeTable),
            ((entities.Attendant, Location), Employee), Seq]) = {

      query map { case ((attendant, location), employee) =>
        (eventId, employee.fullName, employee.id, location.name, location.id, attendant.id,  attendant.insertionDate)
      }
    }

    def attendantsIdsIn(tuples: List[(Int, String, Int, String, Int, Int, DateTime)]): List[Int] = {
      tuples.map(tuple => tuple._6)
    }

    def getResourcesFrom(attendantsIds: List[Int]): Future[List[Seq[AttendantAssignedResource]]] = {

      val resourcesPerAttendant = attendantsIds.map(attendantId =>
        attendantAssignedResourceRepository.getByAttendantIdAndEventId(attendantId)
      )

      Future.sequence(resourcesPerAttendant)
    }

    def mergeAttendantsWithResources(attendants: List[(Int, String, Int, String, Int, Int, DateTime)],
                                     resources: List[Seq[AttendantAssignedResource]])
                                     : Future[List[(Int, String, Int, String, Int, Int, Seq[AttendantAssignedResource], DateTime)]] = {

      Future.successful(attendants zip resources map {
        case (attendant, attendantResources) => (attendant._1, attendant._2, attendant._3, attendant._4, attendant._5,
                                                 attendant._6, attendantResources, attendant._7)
      })
    }

    def map(tuple: (Int, String, Int, String, Int, Int, Seq[AttendantAssignedResource], DateTime)): Attendant = {
      Attendant(tuple._1, Some(tuple._2), tuple._3, Some(tuple._4), tuple._5, tuple._7.toList, Some(tuple._6),
                Some(parse(tuple._8)))
    }

    for {
      attendants          <- fetchAttendants()
      resources           <- getResourcesFrom(attendantsIdsIn(attendants))
      attendantsSummary   <- mergeAttendantsWithResources(attendants, resources)
    } yield attendantsSummary.map(attendantSummary => map(attendantSummary))
  }
}
