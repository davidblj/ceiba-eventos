package application.actions.events

import domain.core.Organizer
import domain.value_objects.ResourceSharedAmount
import javax.inject.Inject

import scala.concurrent.Future

class ChangeResourceAmount @Inject() (organizer: Organizer) {

  def execute(resourceStock: ResourceSharedAmount): Future[None.type] = {
    organizer.set(resourceStock)
  }
}
