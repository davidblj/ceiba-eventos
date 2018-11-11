package domain.repositories

import domain.models.Event
import scala.concurrent.Future

trait EventRepository {
  def createEvent(event: Event): Future[Int]
}