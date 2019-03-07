package controllers

import application.actions.attendant.GetAttendantsByName
import infrastructure.play.json.Validator
import infrastructure.play.json.writes.AttendantsWrites._
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

class AttendantController @Inject()(cc: ControllerComponents, getAttendantsByName: GetAttendantsByName, validator: Validator)
                                   (implicit ec: ExecutionContext)
                                   extends AbstractController(cc){

  def getAttendantsBy(attendantName: String): Action[AnyContent] = Action.async {
    _ => {

      getAttendantsByName.execute(attendantName).map(result => {
        Ok(Json.toJson(result))
      })
    }
  }
}
