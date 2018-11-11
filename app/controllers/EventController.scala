package controllers

import application.actions.events.CreateEvent
import domain.models.Event
import javax.inject._
import play.api.mvc._

@Singleton
class EventController @Inject()(cc: ControllerComponents, createEvent: CreateEvent) extends AbstractController(cc) {

  def CreateEvent() = Action {
    request: Request[AnyContent] => {

      val event = new Event(name = "test", description = "test description")
      val code = createEvent.execute(event)
      Ok(s"injection successfull: resulting code is $code")
    }
  }
}
