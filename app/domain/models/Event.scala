package domain.models

case class Event(name: String,
                 resources: List[Resource],
                 description: Option[String] = None,
                 inputs: Option[List[Input]] = None,
                 eventId: Option[Int] = None) {

  def setId(id: Int): Event = {

    // todo: throw if id is not set to None

    def setResourcesId(): List[Resource] = {
      resources.map(resource => resource.setId(id))
    }

    def setInputsId(): Option[List[Input]] = {
      if (inputs.isDefined) Some(inputs.get.map(input => input.setId(id)))
      else None
    }

    val updatedResources = setResourcesId()
    val updatedInputs = setInputsId()
    Event(name, updatedResources, description, updatedInputs, Some(id))
  }
}