package infrastructure.slick.repositories

import domain.models.{Attendant, AttendantAssignedResource}
import infrastructure.slick.entities.AttendantTable
import infrastructure.slick.transformers._
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class SlickAttendantRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider,
                                          val attendantAssignedResourceRepository: SlickAttendantAssignedResourceRepository,
                                          val employeeRepository: SlickEmployeeRepository)
                                         (implicit ec: ExecutionContext)
                                         extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val attendantTable = TableQuery[AttendantTable]

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

    def getAttendantsAndEmployees : Future[Seq[(Int, Int)]] = {

      val query = attendantTable.filter(_.event_id === eventId).result
      db.run(query)
        .map(attendants => attendants
        .map(attendant => (attendant.id, attendant.employee_id)))
    }

    def getEmployeesNamesFrom(employeesIds: Seq[Int]): Future[Seq[String]] = {

      val employeesNames = employeesIds.map(employeeId => getEmployeeNameBy(employeeId))
      Future.sequence(employeesNames)
    }

    def getEmployeeNameBy(employeeId: Int): Future[String] = {

      employeeRepository.getBy(employeeId).map(employee => employee.fullName)
    }

    def mergeAttendantsAndEmployeesWithEmployeesNames(attendantsTuples: Seq[(Int, Int)], employeesNames: Seq[String])
                                                     : Future[Seq[(Int, Int, String)]] = {

      Future.successful(attendantsTuples zip employeesNames map {
        case ((attendantId, employeeId), employeeName) => (attendantId, employeeId, employeeName)
      })
    }

    def getAssignedResourcesFrom(attendantsIds: Seq[Int]): Future[Seq[List[AttendantAssignedResource]]] = {

      val assignedResources = attendantsIds.map(attendantId => attendantAssignedResourceRepository.getAllBy(attendantId))
      Future.sequence(assignedResources)
    }

    def mergeAttendantsDataWithAssignedResources(attendantsData: Seq[(Int, Int, String)],
                                                assignedResources: Seq[List[AttendantAssignedResource]])
                                                : Future[Seq[(Int, Int, String, List[AttendantAssignedResource])]] = {

      Future.successful(attendantsData zip assignedResources map {
        case ((attendantId, employeeId, employeeName), assignedResource) =>
          (attendantId, employeeId, employeeName, assignedResource)
      })
    }

    def transform(attendantsTuple: Seq[(Int, Int, String, List[AttendantAssignedResource])]): List[Attendant] = {
      attendantsTuple.map(attendant =>
        Attendant(eventId, Some(attendant._3), attendant._2, attendant._4, Some(attendant._1))).toList
    }

    for {
      attendantsAndEmployees               <- getAttendantsAndEmployees
      employeesNames                       <- getEmployeesNamesFrom(attendantsAndEmployees.map(tuple => tuple._2))
      attendantsEmployeesAndEmployeesNames <- mergeAttendantsAndEmployeesWithEmployeesNames(attendantsAndEmployees,
                                              employeesNames)
      attendantsAssignedResources          <- getAssignedResourcesFrom(attendantsEmployeesAndEmployeesNames.map(tuple => tuple._1))
      attendants                           <- mergeAttendantsDataWithAssignedResources(attendantsEmployeesAndEmployeesNames,
                                              attendantsAssignedResources)
    } yield transform(attendants)
  }
}
