package domain.repositories

import domain.models.Event
import scala.concurrent.Future

trait EventRepository {
  def insertEvent(event: Event): Future[Int]
}