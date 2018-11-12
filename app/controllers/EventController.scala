package controllers

import application.actions.events.CreateEvent
import domain.models.{Event, Input, Resource}
import javax.inject._
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class EventController @Inject()(cc: ControllerComponents, createEvent: CreateEvent)
                               (implicit ec: ExecutionContext)
                               extends AbstractController(cc) {

  def CreateEvent() = Action.async {

    request: Request[AnyContent] => {

      val resourceList = List(Resource("test resource", 0, Some("description"), Some(0)))
      val inputList = List(Input("test input", 0, Some("description")))
      val event = Event("test", description = Some("test description"),
        resources = Some(resourceList), inputs = None)

      createEvent.execute(event).map(code => {
        Ok(s"event test insertion resulting code is $code")
      })
    }
  }
}
