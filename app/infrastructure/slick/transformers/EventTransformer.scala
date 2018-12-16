package infrastructure.slick.transformers

import domain.models.Event
import infrastructure.slick.entities.{Event => EventTableObject}

object EventTransformer {

  def toTableObject(event: Event): EventTableObject = {
    EventTableObject(name = event.name, description = event.description, favoriteResource = event.favoriteResource)
  }
}
