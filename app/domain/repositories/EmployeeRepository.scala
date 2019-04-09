package domain.repositories

import domain.entities.Employee

import scala.concurrent.Future

trait EmployeeRepository {
    def getBy(employeeName: String): Future[List[Employee]]
}
