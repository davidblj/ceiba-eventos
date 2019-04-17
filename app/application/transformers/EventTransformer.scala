package application.transformers

import domain.entities.Event
import domain.entities.Input
import domain.entities.Resource
import application.transfer_objects.{OutcomingEvent, IncomingEvent => EventAppObject}
import infrastructure.slick.shared.CustomTypesTransformers.parse

object EventTransformer {

  def applicationToDomainObject(event: EventAppObject): Event = {

    val domainResourcesObject = event.resources.map(resource => Resource(resource.name, resource.price, resource.description, resource.stock))

    val domainInputsObject =
      if (event.inputs.isDefined) {
        Some(event.inputs.get.map(input => Input(input.name, input.price, input.description)))
      } else None

    Event(event.name, null, domainResourcesObject, event.favoriteResource, event.description, domainInputsObject)
  }

  def domainObjectListToApplicationObjectList(events: List[Event]): List[OutcomingEvent] = {
    events.map(event => OutcomingEvent(event.name, parse(event.insertionDate), event.eventId.get,  event.status))
  }
}
