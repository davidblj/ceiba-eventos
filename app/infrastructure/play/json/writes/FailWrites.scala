package infrastructure.play.json.writes

import play.api.libs.json.{Json, OWrites}
import domain.value_objects.Fail

object FailWrites {
  implicit val failWrites: OWrites[Fail] = Json.writes[Fail]
}