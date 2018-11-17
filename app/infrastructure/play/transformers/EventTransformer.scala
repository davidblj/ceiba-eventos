package infrastructure.play.transformers

import domain.models.Event
import domain.models.Input
import domain.models.Resource
import infrastructure.play.json.reads.{Event => EventPlayObject}

object EventTransformer {

  def toDomainObject(event: EventPlayObject): Event = {

    val domainResourcesObject = event.resources.map(resource => Resource(resource.name, resource.price,
      resource.description, resource.stock))

    val domainInputsObject = if (event.inputs.isDefined) {
      Some(event.inputs.get.map(input => Input(input.name, input.price, input.description)))
    } else None

    Event(event.name, domainResourcesObject, event.description, domainInputsObject)
  }
}
