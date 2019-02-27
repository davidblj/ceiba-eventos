package application.actions.events

import domain.core.Organizer
import domain.value_objects
import domain.value_objects.ResourceStock
import javax.inject.Inject

import scala.concurrent.Future

class ChangeResourceAmount @Inject() (organizer: Organizer) {

  def execute(resourceStock: ResourceStock): Future[Either[value_objects.Fail, Any]] = {
    organizer.set(resourceStock)
  }
}
