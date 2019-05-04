package application.actions.events

import domain.services.Organizer
import javax.inject.Inject

import scala.concurrent.Future

class GetAllLocations @Inject() (organizer: Organizer) {

  def execute(): Future[Seq[String]] = {
    organizer.lookUpRegisteredLocations()
  }
}
