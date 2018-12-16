package application.actions.events

import domain.core.Organizer
import javax.inject.Inject

class AddEventLocation @Inject() (organizer: Organizer){

  def execute() = {
    // organizer.addEventLocation(eventId, location)
  }
}
