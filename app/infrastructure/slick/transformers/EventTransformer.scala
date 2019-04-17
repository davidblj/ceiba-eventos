package infrastructure.slick.transformers

import domain.entities.{Event, Input, Resource}
import infrastructure.slick.entities.{Event => EventTableObject}

object EventTransformer {

  def toTableObject(event: Event): EventTableObject = {
    EventTableObject(name = event.name, description = event.description, favoriteResource = event.favoriteResource)
  }

  def toSimpleDomainObject(event: EventTableObject, resources: Option[Seq[Resource]], inputs: Option[Seq[Input]]): Event = {
    Event(event.name, event.insertionDate, resources.getOrElse(Nil).toList, event.description,
      None, Some(inputs.getOrElse(Nil).toList), Some(event.id), event.finished)
  }

  def toSimpleDomainObjectList(events: Seq[EventTableObject]): List[Event] = {
    events.map(event => toSimpleDomainObject(event, None, None)).toList
  }
}
