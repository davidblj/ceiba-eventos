package domain.core

import domain.models.Event
import domain.repositories.EventRepository
import javax.inject.Inject

import scala.concurrent.Future

class Organizer @Inject() (eventRepository: EventRepository) {

  def launchEvent(event: Event): Future[Int] = {

    // todo: leverage the incoming object into our organizer (validate that at least one resource is required)
    eventRepository.insertEvent(event)
  }
}