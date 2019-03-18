package infrastructure.play.json.writes

import domain.models.Resource
import domain.value_objects.EventResources
import play.api.libs.json._

case class ResourcesWrites(favorite_resource: Option[String], resources: Seq[Resource])

object ResourcesWrites {
  implicit val resourceWrites: OWrites[Resource] = Json.writes[Resource]
  implicit val eventResourcesWrites: OWrites[EventResources] = Json.writes[EventResources]
}
