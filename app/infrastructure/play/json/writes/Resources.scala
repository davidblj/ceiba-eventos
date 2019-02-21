package infrastructure.play.json.writes

import domain.models.Resource
import domain.value_objects.EventResources
import play.api.libs.json._

case class Resources(resources: Seq[Resource])

object Resources {
  implicit val resourceWrites: OWrites[Resource] = Json.writes[Resource]
  implicit val eventResourcesWrites: OWrites[EventResources] = Json.writes[EventResources]
}