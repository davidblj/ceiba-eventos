package application.actions.events

import domain.services.Organizer
import domain.value_objects.Location
import javax.inject.Inject

import scala.concurrent.Future

class AddEventLocation @Inject() (organizer: Organizer) {

  def execute(location: Location): Future[Int] = {
    organizer.subscribe(location)
  }
}
