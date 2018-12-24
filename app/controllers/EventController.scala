package controllers

import application.actions.events.CreateEvent
import application.transfer_objects.Event
import infrastructure.play.json.reads.EventReads._
import javax.inject._
import play.api.mvc._
import infrastructure.play.json.Validator
import infrastructure.play.json.writes.Error
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext

@Singleton
class EventController @Inject()(cc: ControllerComponents, CreateEvent: CreateEvent, validator: Validator)
                               (implicit ec: ExecutionContext)
                                extends AbstractController(cc) {

  def createEvent(): Action[Event] = Action.async(validator.validateJson[Event]) {
    request => {

      // todo: return a meaningful response message structure

      val event = request.body
      CreateEvent.execute(event).map(_ => {
        NoContent
      }).recover({
        case e: IllegalArgumentException => UnprocessableEntity(Json.toJson(Error(e.getMessage)))
      })
    }
  }
}
