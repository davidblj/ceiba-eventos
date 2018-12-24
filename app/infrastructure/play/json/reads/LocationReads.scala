package infrastructure.play.json.reads

import domain.data_containers.Location
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

object LocationReads {

  implicit val locationReads: Reads[Location] = (
    (JsPath \ "event_id").read[Int] and
    (JsPath \ "name").read[String](minLength[String](3) keepAnd maxLength[String](24))
  )(Location.apply _)
}
