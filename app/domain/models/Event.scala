package domain.models

case class Event(name: String,
                 resources: List[Resource],
                 favoriteResource: Option[String],
                 description: Option[String] = None,
                 inputs: Option[List[Input]] = None,
                 eventId: Option[Int] = None) {

  require(checkFavoriteResource(), "favorite_resource must match a resource name in the resource item list")

  def setId(id: Int): Event = {
    Event(name, resources, description, favoriteResource, inputs, Some(id))
  }

  // utils

  private def checkFavoriteResource(): Boolean = {
    if (favoriteResource.isDefined) {
      resources.exists((resource: Resource) => resource.name == favoriteResource.get)
    } else true
  }
}