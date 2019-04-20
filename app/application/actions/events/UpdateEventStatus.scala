package application.actions.events

import domain.core.Organizer
import javax.inject.Inject

import scala.concurrent.Future

class UpdateEventStatus @Inject() (organizer: Organizer){

  def execute(eventId: Int): Future[None.type] = {
    organizer.sealEventBy(eventId)
  }
}
