package domain.core

import domain.models.Event
import domain.repositories.EventRepository
import javax.inject.Inject

import scala.concurrent.Future

class Organizer @Inject() (eventRepository: EventRepository) {

  def launchEvent(event: Event): Future[Int] = {

    eventRepository.insertEvent(event)
  }
}