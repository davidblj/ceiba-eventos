package application.actions.events

import application.transfer_objects.OutcomingEvent
import application.transformers.EventTransformer
import domain.core.Organizer
import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class GetAllEventsWithSimpleSignature @Inject() (organizer: Organizer) {

  implicit val ec: ExecutionContext = ExecutionContext.Implicits.global

  def execute(status: Boolean): Future[List[OutcomingEvent]] = {
    organizer.getAllEventsWithSimpleSignatureBy(status)
             .map(events => EventTransformer.domainObjectListToApplicationObjectList(events))
  }
}
