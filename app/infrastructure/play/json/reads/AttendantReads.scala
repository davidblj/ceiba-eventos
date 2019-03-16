package infrastructure.play.json.reads

import application.transfer_objects.{Attendant, AttendantAssignedResource}
import play.api.libs.json.{JsPath, Reads}
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

object AttendantReads {

  implicit val assignedResourceReads: Reads[AttendantAssignedResource] = (
    (JsPath \ "resource_id").read[Int] and
    (JsPath \ "shared_amount").read[Int](verifying(isIntPositive))
  )(AttendantAssignedResource.apply _)

  implicit val attendant: Reads[Attendant] = (
    (JsPath \ "event_id").read[Int] and
    (JsPath \ "employee_id").read[Int] and
    (JsPath \ "assigned_resources").read[List[AttendantAssignedResource]]
  )(Attendant.apply _)

  private def isIntPositive(i: Int) = i > 0
}
