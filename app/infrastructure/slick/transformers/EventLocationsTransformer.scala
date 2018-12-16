package infrastructure.slick.transformers

import infrastructure.slick.entities.{EventLocations => EventLocationsObject}

object EventLocationsTransformer {

  def toTableObject(eventId: Int, locationId: Int): EventLocationsObject = {
    EventLocationsObject(eventId, locationId)
  }
}
