package domain.repositories

import domain.models.Event

trait EventRepository {
  def createEvent(event: Event): Int
}