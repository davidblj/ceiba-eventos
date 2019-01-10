package domain.repositories

import domain.data_containers.Location
import domain.models.Event
import domain.models.Resource

import scala.concurrent.Future

trait EventRepository {
  def add(event: Event): Future[Int]
  def addLocation(location: Location): Future[Int]
  def getResourcesBy(eventId: Int): Future[Seq[Resource]]
}
