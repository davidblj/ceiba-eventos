package domain.repositories

import domain.models.Employee

import scala.concurrent.Future

trait EmployeeRepository {
    def getBy(employeeName: String): Future[List[Employee]]
}
