package domain.models

case class Event(name: String,
                 description: Option[String] = None,
                 resources: Option[List[Resource]] = None,
                 inputs: Option[List[Input]] = None,
                 eventId: Option[Int] = None) {

  def setId(id: Int): Event = {

    // todo: throw if id is not set to None
    def setResourcesId(): Option[List[Resource]] = {
      if (resources.isDefined) Some(resources.get.map(resource => resource.setId(id)))
      else None
    }

    def setInputsId(): Option[List[Input]] = {
      if (inputs.isDefined) Some(inputs.get.map(input => input.setId(id)))
      else None
    }

    val updatedResources = setResourcesId()
    val updatedInputs = setInputsId()
    Event(name, description, updatedResources, updatedInputs, Some(id))
  }
}