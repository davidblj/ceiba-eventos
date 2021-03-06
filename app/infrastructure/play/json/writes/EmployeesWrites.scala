package infrastructure.play.json.writes

import application.transfer_objects.Employees
import domain.entities.Employee
import play.api.libs.functional.syntax._
import play.api.libs.json._

object EmployeesWrites {

  implicit val employeeWrites: OWrites[Employee] = (
    (JsPath \ "full_name").write[String] and
    (JsPath \ "id").writeNullable[Int]
  )(unlift(Employee.unapply))

  implicit val employeesWrites: OWrites[Employees] = Json.writes[Employees]
}
