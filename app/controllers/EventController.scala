package controllers

import application.actions.events.CreateEvent
import infrastructure.play.json.reads.Event
import javax.inject._
import play.api.mvc._
import infrastructure.play.json.Validator
import infrastructure.play.json.writes.Error
import infrastructure.play.transformers.EventTransformer
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext

@Singleton
class EventController @Inject()(cc: ControllerComponents, createEvent: CreateEvent, validator: Validator)
                               (implicit ec: ExecutionContext)
                               extends AbstractController(cc) {

  def CreateEvent(): Action[Event] = Action.async(validator.validateJson[Event]) {
    request => {

      val event = request.body
      val domainEventObject = EventTransformer.toDomainObject(event)

      // todo: add a meaningful response message
      createEvent.execute(domainEventObject).map(_ => {
        NoContent
      }).recover( {
        case e: IllegalArgumentException => UnprocessableEntity(Json.toJson(Error(e.getMessage)))
      })
    }
  }
}
