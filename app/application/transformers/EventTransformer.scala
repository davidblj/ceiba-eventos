package application.transformers

import domain.models.Event
import domain.models.Input
import domain.models.Resource
import application.transfer_objects.{Event => EventAppObject}

object EventTransformer {

  def applicationToDomainObject(event: EventAppObject): Event = {

    val domainResourcesObject = event.resources.map(resource => Resource(resource.name, resource.price, resource.description, resource.stock))

    val domainInputsObject = if (event.inputs.isDefined) {
      Some(event.inputs.get.map(input => Input(input.name, input.price, input.description)))
    } else None

    Event(event.name, domainResourcesObject, event.description, event.favoriteResource, domainInputsObject)
  }
}
