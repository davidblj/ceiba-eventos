package domain.aggregates

import domain.entities.Employee

import scala.concurrent.Future

trait EmployeeRepository {
    def getBy(employeeName: String): Future[List[Employee]]
}
