package infrastructure.slick.repositories

import domain.models.{Attendant, AttendantAssignedResource, Resource}
import infrastructure.slick.entities
import infrastructure.slick.entities._
import infrastructure.slick.transformers._
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.QueryBase

import scala.collection.immutable
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

    def fetchAttendants(): Future[List[(Int, String, Int, String, Int, Int)]] = {

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
        (eventId, employee.fullName, employee.id, location.name, location.id, attendant.id)
      }
    }

    def attendantsIdsIn(tuples: List[(Int, String, Int, String, Int, Int)]) = {
      tuples.map(tuple => tuple._6)
    }

    def getResourcesFrom(attendantsIds: List[Int]): Future[List[List[AttendantAssignedResource]]] = {

      val resourcesPerAttendant = attendantsIds
        .map(attendantId => attendantAssignedResourceRepository.getAllBy(attendantId))
      Future.sequence(resourcesPerAttendant)
    }

    def mergeAttendantsWithResources(attendants: List[(Int, String, Int, String, Int, Int)],
                                     resources: List[List[AttendantAssignedResource]])
                                     : Future[List[(Int, String, Int, String, Int, Int, List[AttendantAssignedResource])]] = {

      Future.successful(attendants zip resources map {
        case (attendant, attendantResources) => (attendant._1, attendant._2, attendant._3, attendant._4, attendant._5,
                                                 attendant._6, attendantResources)
      })
    }

    def map(tuple: (Int, String, Int, String, Int, Int, List[AttendantAssignedResource])): Attendant = {
      Attendant(tuple._1, Some(tuple._2), tuple._3, Some(tuple._4), tuple._5, tuple._7, Some(tuple._6))
    }

    for {
      attendants          <- fetchAttendants()
      resources           <- getResourcesFrom(attendantsIdsIn(attendants))
      attendantsSummary   <- mergeAttendantsWithResources(attendants, resources)
    } yield attendantsSummary.map(attendantSummary => map(attendantSummary))
  }
}
