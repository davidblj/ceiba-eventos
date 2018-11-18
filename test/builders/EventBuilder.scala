package builders

import domain.models.{Event, Input, Resource}

// todo: https://medium.com/@maximilianofelice/builder-pattern-in-scala-with-phantom-types-3e29a167e863
class EventBuilder {

  var name: String = "Children's day"
  var description: Option[String] = Some("Recognized day to celebrate children")
  var favoriteResource: Option[String] = Some("Superman toy")
  var resources: List[Resource] = List(Resource("Superman toy", 9.9f, stock = Some(30)))
  var inputs: Option[List[Input]] = Some(List(Input("bombs", 6f)))

  def build(): Event = Event(name, resources, favoriteResource, description, inputs)

  def withName() = {}
  def withDescription() = {}
  def withFavoriteResource() = {}
  def withResources() = {}
  def withInputs() = {}
}
