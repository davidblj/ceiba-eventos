package infrastructure.play.json.reads

import application.transfer_objects.{AssignedResource, Attendant}
import play.api.libs.json.{JsPath, Reads}
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

object AttendantReads {

  /*// todo: verifying that is positive.
  implicit val assignedResourceReads: Reads[AssignedResource] = (
    (JsPath \ "resource_id").read[Int] and
    (JsPath \ "assignedAmount").read[Int]
  )(AssignedResource.apply _)

  implicit val attendant: Reads[Attendant] = {
    (JsPath \ "full_name").read[String](minLength[String](3) keepAnd maxLength[String](24)) and
      (JsPath \ "assigned_resources").read[List]
  }*/
}
