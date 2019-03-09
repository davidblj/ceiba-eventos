package controllers

import application.actions.attendant.GetEmployeesByName
import infrastructure.play.json.Validator
import infrastructure.play.json.writes.EmployeesWrites._
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

class EmployeesController @Inject()(cc: ControllerComponents, getEmployeesByName: GetEmployeesByName, validator: Validator)
                                   (implicit ec: ExecutionContext)
                                   extends AbstractController(cc){

  def getEmployeesBy(employeeName: String): Action[AnyContent] = Action.async {
    _ => {

      getEmployeesByName.execute(employeeName).map(result => {
        Ok(Json.toJson(result))
      })
    }
  }
}
