package infrastructure.play.json.writes

import play.api.libs.json.{Json, OWrites}

case class Error(message: String)

object Error {
  implicit val errorWrites: OWrites[Error] = Json.writes[Error]
}