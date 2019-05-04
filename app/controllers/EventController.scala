package controllers

import application.actions.events.{CreateEvent, GetAllEventsWithSimpleSignature, GetEventSummary, UpdateEventStatus}
import application.transfer_objects.IncomingEvent
import domain.value_objects.Fail
import infrastructure.play.json.Validator
import infrastructure.play.json.reads.EventReads._
import infrastructure.play.json.writes.EventSummaryWrites.eventSummaryWrites
import infrastructure.play.json.writes.EventsWithSimpleSignatureWrites.eventWrites
import infrastructure.play.json.writes.FailWrites.failWrites
import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class EventController @Inject()(cc: ControllerComponents, CreateEvent: CreateEvent, GetEvent: GetEventSummary,
                                GetEventsWithSimpleSignature: GetAllEventsWithSimpleSignature,
                                UpdateEventStatus: UpdateEventStatus, validator: Validator)
                               (implicit ec: ExecutionContext)
                                extends AbstractController(cc) {

  def createEvent(): Action[IncomingEvent] = Action.async(validator.validateJson[IncomingEvent]) {
    request => {

      val event = request.body
      CreateEvent.execute(event).map(_ => {
        NoContent
      }).recover({
        case e: IllegalArgumentException => UnprocessableEntity(Json.toJson(Fail(e.getMessage)))
      })
    }
  }

  def getEventSummaryBy(id: Int): Action[AnyContent] = Action.async {
    _ => {

      GetEvent.execute(id).map(eventSummary => {
        Ok(Json.toJson(eventSummary))
      })
    }
  }

  def getEventsWithSimpleSignatureBy(status: Int): Action[AnyContent] = Action.async {
    _ => {

      val eventStatus = if (status == 1 ) true else false
      GetEventsWithSimpleSignature.execute(eventStatus).map(events => {
        Ok(Json.toJson(events))
      })
    }
  }

  def updateEventStatusBy(eventId: Int): Action[AnyContent] = Action.async {
    _ => {

      UpdateEventStatus.execute(eventId).map(_ => {
        NoContent
      }).recover({
        case e: Exception => InternalServerError(Json.toJson(Fail(e.getMessage)))
      })
    }
  }
}
