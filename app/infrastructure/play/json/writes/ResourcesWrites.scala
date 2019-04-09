package infrastructure.play.json.writes

import domain.entities.Resource
import domain.value_objects.EventResources
import play.api.libs.json._

object ResourcesWrites {
  implicit val resourceWrites: OWrites[Resource] = Json.writes[Resource]
  implicit val eventResourcesWrites: OWrites[EventResources] = Json.writes[EventResources]
}
