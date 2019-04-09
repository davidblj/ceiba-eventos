package infrastructure.slick.transformers

import domain.entities.Resource
import infrastructure.slick.entities.{Resource => ResourceTableObject}

object ResourceTransformer {

  def toTableObject(resource: Resource, eventId: Int): ResourceTableObject = {
    ResourceTableObject(event_id = eventId, name = resource.name, description = resource.description,
                        price = resource.price, stock = resource.stock)
  }

  def toTableObjectList(resources: List[Resource], eventId: Int): List[ResourceTableObject] = {
    resources.map(resource => toTableObject(resource, eventId))
  }

  def toDomainObject(resource: ResourceTableObject): Resource = {
    Resource(resource.name, resource.price, resource.description, resource.stock, Some(resource.id),
             resource.quantity)
  }

  def toDomainObjectList(resources: Seq[ResourceTableObject]): Seq[Resource] = {
    resources.map(resource => toDomainObject(resource))
  }
}