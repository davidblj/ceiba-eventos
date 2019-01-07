package application.actions.events

import domain.core.Organizer
import domain.data_containers.Location
import javax.inject.Inject

import scala.concurrent.Future

class AddEventLocation @Inject() (organizer: Organizer){

  def execute(location: Location): Future[Int] = {
    organizer.subscribeLocation(location)
  }
}
