package infrastructure.slick.repositories

import domain.models.Event
import domain.repositories.EventRepository

class SlickEventRepository extends EventRepository {
  override def createEvent(event: Event): Int = 24
}