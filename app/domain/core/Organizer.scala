package domain.core

import domain.models.Event
import domain.repositories.EventRepository
import javax.inject.Inject

import scala.concurrent.Future

class Organizer @Inject() (eventRepository: EventRepository) {

  def launchEvent(event: Event): Future[Int] = {
    // todo: handle an empty resource list (left unhandled in the controller)
    // todo: handle the name and description minimun length and maximun length (left unhandled in the controller)
    eventRepository.insertEvent(event)
  }
}