package application.actions.events

import domain.core.Organizer
import domain.value_objects.ResourceQuantityAmount
import javax.inject.Inject

import scala.concurrent.Future

class ChangeResourceAmount @Inject() (organizer: Organizer) {

  def execute(resourceStock: ResourceQuantityAmount): Future[None.type] = {
    organizer.set(resourceStock)
  }
}
