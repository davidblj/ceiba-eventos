package infrastructure.slick.transformers

import domain.models.Input
import infrastructure.slick.entities.{Input => InputTableObject}

object InputTransformer {

  def toTableObjectList(inputs: List[Input]): List[InputTableObject] = {
    inputs.map(input => toTableObject(input))
  }

  def toTableObject(input: Input): InputTableObject = {
    // the eventId is mandatory, otherwise, it throws "no such element exception"
    InputTableObject(event_id = input.eventId.get, name = input.name, description = input.description,
                     price = input.price)
  }
}
