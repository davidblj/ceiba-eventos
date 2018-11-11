package application.actions.events

import domain.core.Organizer
import domain.models.Event
import javax.inject.Inject

class CreateEvent @Inject() (organizer: Organizer) {

  def execute(event: Event): Int = {

    // todo: leverage the incoming object into our organizer (if applies)
    organizer.launchEvent(event)
  }
}