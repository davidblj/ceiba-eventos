package application.actions.events

import domain.core.Organizer
import domain.value_objects.EventResources
import javax.inject.Inject

import scala.concurrent.Future

class GetAllResources @Inject() (organizer: Organizer) {

  def execute(eventId: Int): Future[EventResources] = {
    organizer.lookUpResourcesBy(eventId)
  }
}
