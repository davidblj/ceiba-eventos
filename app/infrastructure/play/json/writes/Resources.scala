package infrastructure.play.json.writes

import domain.models.Resource
import play.api.libs.json._

case class Resources(resources: Seq[Resource])

object Resources {
  implicit val resourceWrites: OWrites[Resource] = Json.writes[Resource]
  implicit val resourcesWrites: OWrites[Resources] = Json.writes[Resources]
}
