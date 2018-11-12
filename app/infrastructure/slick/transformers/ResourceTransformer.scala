package infrastructure.slick.transformers

import domain.models.Resource
import infrastructure.slick.entities.{Resource => ResourceTableObject}

object ResourceTransformer {

  def toTableObjectList(resources: List[Resource]): List[ResourceTableObject] = {
    resources.map(resource => toTableObject(resource))
  }

  def toTableObject(resource: Resource): ResourceTableObject = {
    // the eventId is mandatory, otherwise, it throws "no such element exception"
    ResourceTableObject(event_id = resource.eventId.get, name = resource.name, description = resource.description,
                        price = resource.price, stock = resource.stock)
  }
}