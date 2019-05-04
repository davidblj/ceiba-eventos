package application.actions.events

import domain.services.Organizer
import domain.entities.EventSummary
import javax.inject.Inject

import scala.concurrent.Future

class GetEventSummary @Inject()(organizer: Organizer) {

  def execute(eventId: Int): Future[EventSummary] = {
    organizer.summarizeEventBy(eventId)
  }
}
