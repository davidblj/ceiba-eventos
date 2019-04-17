package domain.entities

import org.joda.time.DateTime

case class Event(name: String,
                 insertionDate: DateTime,
                 resources: List[Resource],
                 favoriteResource: Option[String] = None,
                 description: Option[String] = None,
                 inputs: Option[List[Input]] = None,
                 eventId: Option[Int] = None,
                 status: Boolean = false) {

  require(checkFavoriteResource(), "favorite_resource must match a resource name in the resource item list")

  def setId(id: Int): Event = {
    Event(name, insertionDate, resources, description, favoriteResource, inputs, Some(id))
  }

  // utils

  private def checkFavoriteResource(): Boolean = {
    if (favoriteResource.isDefined) {
      resources.exists((resource: Resource) => resource.name == favoriteResource.get)
    } else true
  }
}