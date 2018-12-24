package domain.repositories

import domain.data_containers.Location
import domain.models.Event
import scala.concurrent.Future

trait EventRepository {
  def add(event: Event): Future[Int]
  def addLocation(location: Location): Future[Int]
}