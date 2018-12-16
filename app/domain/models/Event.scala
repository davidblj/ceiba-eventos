package domain.models

case class Event(name: String,
                 resources: List[Resource],
                 favoriteResource: Option[String],
                 description: Option[String] = None,
                 inputs: Option[List[Input]] = None,
                 eventId: Option[Int] = None) {

  require(checkFavoriteResource(), "favorite_resource must match a resource name in the resource item list")

  // todo: this implementation should change, the id is set only to avoid code witting in the transformers. Change it.
  def setId(id: Int): Event = {

    def setResourcesId(): List[Resource] = {
      resources.map(resource => resource.setId(id))
    }

    def setInputsId(): Option[List[Input]] = {
      if (inputs.isDefined) Some(inputs.get.map(input => input.setId(id)))
      else None
    }

    val updatedResources = setResourcesId()
    val updatedInputs = setInputsId()
    Event(name, updatedResources, description, favoriteResource, updatedInputs, Some(id))
  }

  // utils

  private def checkFavoriteResource(): Boolean = {

    if (favoriteResource.isDefined) {
      resources.exists((resource: Resource) => resource.name == favoriteResource.get)
    } else true
  }
}