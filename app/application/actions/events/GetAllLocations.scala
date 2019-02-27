package application.actions.events

import domain.core.Organizer
import javax.inject.Inject

import scala.concurrent.Future

class GetAllLocations @Inject() (organizer: Organizer) {

  def execute(): Future[Seq[String]] = {
    organizer.lookUpEveryLocation()
  }
}
