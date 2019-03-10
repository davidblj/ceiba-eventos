package infrastructure.play.json.writes

import application.transfer_objects.Employees
import domain.models.{AttendantAssignedResource, Employee}
import play.api.libs.json._

object EmployeesWrites {
  implicit val employeeWrites: OWrites[Employee] = Json.writes[Employee]
  implicit val employeesWrites: OWrites[Employees] = Json.writes[Employees]
}
