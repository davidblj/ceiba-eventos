package application.actions.events

import domain.core.Organizer
import domain.models.Resource
import javax.inject.Inject

import scala.concurrent.Future

class GetAllResources @Inject() (organizer: Organizer) {

  def execute(eventId: Int): Future[Seq[Resource]] = {
    organizer.lookUpResourcesBy(eventId)
  }

}
