package domain.repositories

import domain.value_objects.Location
import domain.models.Event

import scala.concurrent.Future

trait EventRepository {
  def getEventBy(id: Int): Future[Event]
  def add(event: Event): Future[Int]
  def addLocation(location: Location): Future[Int]
}
