package application.actions.events

import application.transfer_objects.{IncomingEvent => EventTransferObject}
import application.transformers.EventTransformer
import domain.services.Organizer
import javax.inject.Inject

import scala.concurrent.Future

class CreateEvent @Inject() (organizer: Organizer) {

  def execute(event: EventTransferObject): Future[Int] = {

    val domainEventObject = EventTransformer.applicationToDomainObject(event)
    organizer.launch(domainEventObject)
  }
}