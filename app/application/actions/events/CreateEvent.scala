package application.actions.events

import application.transfer_objects.{Event => EventTransferObject}
import application.transformers.EventTransformer
import domain.core.Organizer
import javax.inject.Inject

import scala.concurrent.Future

class CreateEvent @Inject() (organizer: Organizer) {

  def execute(event: EventTransferObject): Future[Int] = {

    val domainEventObject = EventTransformer.toDomainObject(event)
    organizer.launchEvent(domainEventObject)
  }
}