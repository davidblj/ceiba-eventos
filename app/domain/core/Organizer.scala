package domain.core

import domain.data_containers.Location
import domain.models.Event
import domain.repositories.{EventRepository, LocationRepository}
import javax.inject.Inject

import scala.concurrent.Future

// todo: move this class into a new package (think out the aggregate structure)
class Organizer @Inject() (eventRepository: EventRepository, locationRepository: LocationRepository) {

  def launchEvent(event: Event): Future[Int] = {
    // todo: handle every property requirement (empty resource list & properties characters length -in the model-)
    eventRepository.add(event)
  }

  def subscribeLocation(location: Location): Future[Int] = {
    // todo: handle a non existent event id (get event by id, -in the repo-)
    eventRepository.addLocation(location)
  }

  def lookUpAllLocations(): Future[Seq[String]] = {
    locationRepository.getAll
  }
}
