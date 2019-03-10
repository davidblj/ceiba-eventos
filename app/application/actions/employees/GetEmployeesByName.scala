package application.actions.employees

import application.transfer_objects.Employees
import domain.core.Organizer
import javax.inject.Inject

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class GetEmployeesByName @Inject()(organizer: Organizer)  {

  def execute(employeeName: String): Future[Employees] = {
    organizer.getEmployeesBy(employeeName).map(employees => Employees(employees))
  }
}
