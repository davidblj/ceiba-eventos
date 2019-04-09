package infrastructure.play.json.writes

import domain.value_objects.User
import play.api.libs.json.{Json, Writes}

object UserWrites {
  implicit val userWrites: Writes[User] = Json.writes[User]
}
