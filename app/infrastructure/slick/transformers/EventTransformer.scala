package infrastructure.slick.transformers

import domain.models.{Event, Resource}
import infrastructure.slick.entities.{Event => EventTableObject}

object EventTransformer {

  def toTableObject(event: Event): EventTableObject = {
    EventTableObject(name = event.name, description = event.description, favoriteResource = event.favoriteResource)
  }

  def toDomainObject(event: EventTableObject, resources: Seq[Resource]): Event = {
    Event(event.name, resources.toList, event.favoriteResource, event.favoriteResource, null, Some(event.id))
  }
}
