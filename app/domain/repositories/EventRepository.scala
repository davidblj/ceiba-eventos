package domain.repositories

import domain.value_objects.{Location, ResourceSharedAmount}
import domain.models.{Attendant, Event, EventSummary, Resource}

import scala.concurrent.Future

trait EventRepository {
  def getBy(id: Int): Future[Event]
  def getSummaryBy(id: Int): Future[EventSummary]
  def getResourceBy(resourceId: Int): Future[Resource]
  def add(event: Event): Future[Int]
  def add(location: Location): Future[Int]
  def add(attendant: Attendant): Future[Int]
  def set(resourceQuantityAmount: ResourceSharedAmount): Future[Int]
}
