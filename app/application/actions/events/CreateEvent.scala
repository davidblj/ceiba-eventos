package application.actions.events

import domain.core.Organizer
import domain.models.Event
import javax.inject.Inject
import scala.concurrent.Future

class CreateEvent @Inject() (organizer: Organizer) {

  def execute(event: Event): Future[Int] = {
    organizer.launchEvent(event)
  }
}