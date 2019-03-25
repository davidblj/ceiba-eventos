package application.actions.events

import domain.core.Organizer
import domain.models.EventSummary
import javax.inject.Inject

import scala.concurrent.Future

class GetEvent @Inject() (organizer: Organizer) {

  def execute(eventId: Int): Future[EventSummary] = {
    organizer.summarizeEventBy(eventId)
  }
}
