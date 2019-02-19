package domain.core

import domain.models.Event
import domain.repositories.{EventRepository, LocationRepository}
import domain.value_objects.Location
import domain.value_objects.EventResources
import javax.inject.Inject

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

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

  def lookUpResourcesBy(eventId: Int): Future[EventResources] = {
    eventRepository.getEventBy(eventId).map(event => EventResources(event.favoriteResource, event.resources))
  }
}
