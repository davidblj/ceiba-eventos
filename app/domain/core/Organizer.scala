package domain.core

import domain.data_containers.Location
import domain.models.Event
import domain.repositories.EventRepository
import javax.inject.Inject
import scala.concurrent.Future

class Organizer @Inject() (eventRepository: EventRepository) {

  def launchEvent(event: Event): Future[Int] = {
    // todo: handle every property requirement (empty resource list & properties characters length -in the model-)
    eventRepository.add(event)
  }

  def addEventLocation(location: Location): Future[Int] = {
    // todo: handle a non existent event id (get event by id)
    eventRepository.addLocation(location)
  }
}
