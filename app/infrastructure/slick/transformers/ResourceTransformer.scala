package infrastructure.slick.transformers

import domain.models.Resource
import infrastructure.slick.entities.{Resource => ResourceTableObject}

object ResourceTransformer {

  def toTableObjectList(resources: List[Resource], eventId: Int): List[ResourceTableObject] = {
    resources.map(resource => toTableObject(resource, eventId))
  }

  def toTableObject(resource: Resource, eventId: Int): ResourceTableObject = {
    // the eventId is mandatory, otherwise, it throws "no such element exception"
    ResourceTableObject(event_id = eventId, name = resource.name, description = resource.description,
                        price = resource.price, stock = resource.stock)
  }

  def toDomainObjectList(resources: Seq[ResourceTableObject]): Seq[Resource] = {
    resources.map(resource => Resource(resource.name, resource.price, resource.description, resource.stock))
  }
}