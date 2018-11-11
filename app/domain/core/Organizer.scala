package domain.core

import domain.models.Event
import domain.repositories.EventRepository
import javax.inject.Inject

class Organizer @Inject() (eventRepository: EventRepository) {

  def launchEvent(event: Event): Int = {

    // todo: use the repo
    // todo: call the supply manager to store the repo and input
    eventRepository.createEvent(event)
  }
}