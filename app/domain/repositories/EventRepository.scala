package domain.repositories

import domain.value_objects.{Location, ResourceQuantityAmount}
import domain.models.{Event, Resource}

import scala.concurrent.Future

trait EventRepository {
  def getBy(id: Int): Future[Event]
  def getResourceBy(resourceId: Int): Future[Resource]
  def add(event: Event): Future[Int]
  def addLocation(location: Location): Future[Int]
  def set(resourceQuantityAmount: ResourceQuantityAmount): Future[Int]
}
