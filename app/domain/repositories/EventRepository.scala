package domain.repositories

import domain.value_objects.{Location, ResourceStock}
import domain.models.{Event, Resource}

import scala.concurrent.Future

trait EventRepository {
  def getBy(id: Int): Future[Event]
  def getResourceBy(resourceId: Int): Future[Resource]
  def add(event: Event): Future[Int]
  def addLocation(location: Location): Future[Int]
  def set(resourceStock: ResourceStock): Future[Int]
}
