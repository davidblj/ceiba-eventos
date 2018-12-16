package infrastructure.play.json.reads

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

// todo: this objects should be defined inside the app layer
//       the convertion to domain objects should be also done inside
//       the app layer

case class Input(name: String,
                 price: Float,
                 description: Option[String])

case class Resource(name: String,
                    price: Float,
                    description: Option[String],
                    stock: Option[Int])

case class Event(name: String,
                 resources: List[Resource],
                 favoriteResource: Option[String],
                 description: Option[String],
                 inputs: Option[List[Input]])

object Event {

  implicit val resourceReads: Reads[Resource] = (
    (JsPath \ "name").read[String](minLength[String](3) keepAnd maxLength[String](24)) and
    (JsPath \ "price").read[Float](verifying(isFloatPositive)) and
    (JsPath \ "description").readNullable[String](minLength[String](10) keepAnd maxLength[String](100)) and
    (JsPath \ "stock").readNullable[Int](verifying(isIntPositive))
    )(Resource.apply _)

  implicit val inputReads: Reads[Input] = (
    (JsPath \ "name").read[String](minLength[String](3) keepAnd maxLength[String](24)) and
    (JsPath \ "price").read[Float](verifying(isFloatPositive)) and
    (JsPath \ "description").readNullable[String](minLength[String](10) keepAnd maxLength[String](100))
    )(Input.apply _)

  implicit val eventReads: Reads[Event] = (
    (JsPath \ "name").read[String](minLength[String](3) keepAnd maxLength[String](24)) and
    (JsPath \ "resources").read[List[Resource]](minLength[List[Resource]](1)) and
    (JsPath \ "favorite_resource").readNullable[String] and
    (JsPath \ "description").readNullable[String](minLength[String](10) keepAnd maxLength[String](100)) and
    (JsPath \ "inputs").readNullable[List[Input]]
    )(Event.apply _)

  private def isFloatPositive(i: Float) = i > 0
  private def isIntPositive(i: Int) = i > 0
}
