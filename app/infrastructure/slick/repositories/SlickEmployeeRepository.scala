package infrastructure.slick.repositories

import domain.entities.Employee
import domain.aggregates.EmployeeRepository
import infrastructure.slick.entities.EmployeeTable
import infrastructure.slick.transformers.EmployeeTransformer
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class SlickEmployeeRepository @Inject()(val dbConfigProvider: DatabaseConfigProvider)
                                       (implicit ec: ExecutionContext)
                                       extends HasDatabaseConfigProvider[JdbcProfile] with EmployeeRepository {
  import profile.api._
  
  val employeeTable = TableQuery[EmployeeTable]

  def getBy(employeeName: String): Future[List[Employee]] = {

    val query = employeeTable.filter(_.fullName like s"%$employeeName%").result
    db.run(query).map(employee => EmployeeTransformer.toDomainObjectList(employee))
  }

  def getBy(employeeId: Int): Future[Employee] = {

    val query = employeeTable.filter(_.id === employeeId).result.head
    db.run(query).map(employee => EmployeeTransformer.toDomainObject(employee))
  }
}
