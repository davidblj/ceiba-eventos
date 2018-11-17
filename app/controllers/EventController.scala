package controllers

import application.actions.events.CreateEvent
import infrastructure.play.json.reads.Event
import javax.inject._
import play.api.mvc._
import infrastructure.play.json.Validator
import infrastructure.play.transformers.EventTransformer

import scala.concurrent.ExecutionContext

@Singleton
class EventController @Inject()(cc: ControllerComponents, createEvent: CreateEvent, validator: Validator)
                               (implicit ec: ExecutionContext)
                               extends AbstractController(cc) {

  def CreateEvent(): Action[Event] = Action.async(validator.validateJson[Event]) {
    request => {

      /*val resourceList = List(Resource("test resource", 0, Some("description"), Some(0)))
      val inputList = List(Input("test input", 0, Some("description")))
      val event = Event("test", description = Some("test description"),
      resources = resourceList, inputs = None)*/

      val event = request.body
      val domainEventObject = EventTransformer.toDomainObject(event)

      createEvent.execute(domainEventObject).map(code => {
        Ok(s"event test insertion resulting code is $code")
      })
    }
  }
}
