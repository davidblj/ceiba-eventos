package controllers

import application.actions.attendants.SignUpAttendant
import application.transfer_objects.Attendant
import infrastructure.play.json.Validator
import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import infrastructure.play.json.reads.AttendantReads._

import scala.concurrent.ExecutionContext

class AttendantController @Inject()(cc: ControllerComponents, SignUpAttendant: SignUpAttendant, validator: Validator)
                                   (implicit ec: ExecutionContext)
                                   extends AbstractController(cc){

  // todo: you must verify that:
  // todo: 1, the assigned resource exists
  // todo: 2, the event id exists
  // todo: 3, the employee id exists
  // todo: remove the employee from this post payload
  def signUpAttendant(): Action[Attendant] = Action.async(validator.validateJson[Attendant]) {
    request => {

      val attendant = request.body
      SignUpAttendant.execute(attendant).map(_ => {
        NoContent
      })
    }
  }
}
