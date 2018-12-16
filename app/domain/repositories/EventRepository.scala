package domain.repositories

import domain.models.Event
import scala.concurrent.Future

trait EventRepository {
  def add(event: Event): Future[Int]
  def addLocation(id: Int, location: String): Future[Int]
}