package domain.core

import domain.models.Event
import domain.repositories.EventRepository
import javax.inject.Inject

import scala.concurrent.Future

class Organizer @Inject() (eventRepository: EventRepository) {

  def launchEvent(event: Event): Future[Int] = {
    // todo: handle every property requirement (empty resource list & properties characters length)
    eventRepository.add(event)
  }

  def addEventLocation(eventId: Int, location: String): Future[Int] = {
    // todo: handle a non existent event id
    eventRepository.addLocation(eventId, location)
  }
}
