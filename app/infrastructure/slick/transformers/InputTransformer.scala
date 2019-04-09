package infrastructure.slick.transformers

import domain.entities.Input
import infrastructure.slick.entities.{Input => InputTableObject}

object InputTransformer {

  def toTableObjectList(inputs: List[Input], eventId: Int): List[InputTableObject] = {
    inputs.map(input => toTableObject(input, eventId))
  }

  def toTableObject(input: Input, eventId: Int): InputTableObject = {
    // the eventId is mandatory, otherwise, it throws "no such element exception"
    InputTableObject(event_id = eventId, name = input.name, description = input.description,
                     price = input.price)
  }
}
