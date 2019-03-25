package controllers

import application.actions.events.{CreateEvent, GetEvent}
import application.transfer_objects.Event
import domain.value_objects.Fail
import infrastructure.play.json.reads.EventReads._
import javax.inject._
import play.api.mvc._
import infrastructure.play.json.Validator
import infrastructure.play.json.writes.FailWrites.failWrites
import infrastructure.play.json.writes.EventSummaryWrites.eventSummaryWrites
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext

@Singleton
class EventController @Inject()(cc: ControllerComponents, CreateEvent: CreateEvent, GetEvent: GetEvent,
                                validator: Validator)
                               (implicit ec: ExecutionContext)
                                extends AbstractController(cc) {

  def createEvent(): Action[Event] = Action.async(validator.validateJson[Event]) {
    request => {

      val event = request.body
      CreateEvent.execute(event).map(_ => {
        NoContent
      }).recover({
        case e: IllegalArgumentException => UnprocessableEntity(Json.toJson(Fail(e.getMessage)))
      })
    }
  }

  def getEvent(eventId: Int): Action[AnyContent] = Action.async {
    _ => {

      GetEvent.execute(eventId).map(eventSummary => {
        Ok(Json.toJson(eventSummary))
      })
    }
  }

}
