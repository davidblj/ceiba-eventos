package infrastructure.slick.transformers

import infrastructure.slick.entities.{Location => LocationTableObject}

object LocationTransformer {

  def toTableObject(location: String): LocationTableObject = {
    LocationTableObject(name = location)
  }
}
