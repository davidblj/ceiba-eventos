package infrastructure.play.json.writes

import application.transfer_objects.Attendants
import domain.models.Attendant
import domain.value_objects.AssignedResource
import play.api.libs.json._

object AttendantsWrites {
  implicit val assignedResourceWrites: OWrites[AssignedResource] = Json.writes[AssignedResource]
  implicit val attendantWrites: OWrites[Attendant] = Json.writes[Attendant]
  implicit val attendantsWrites: OWrites[Attendants] = Json.writes[Attendants]
}
