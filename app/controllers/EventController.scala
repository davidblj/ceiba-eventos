package controllers

import application.actions.events.CreateEvent
import domain.models.Event
import javax.inject._
import play.api.mvc._
import scala.concurrent.ExecutionContext

@Singleton
class EventController @Inject()(cc: ControllerComponents, createEvent: CreateEvent)
                               (implicit ec: ExecutionContext)
                               extends AbstractController(cc) {

  def CreateEvent() = Action.async {

    request: Request[AnyContent] => {

      val event = new Event("test", description = Some("test description"))
      createEvent.execute(event).map(code => {
        Ok(s"event test insertion resulting code is $code")
      })
    }
  }
}
