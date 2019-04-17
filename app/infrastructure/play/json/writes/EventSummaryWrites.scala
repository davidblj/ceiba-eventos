package infrastructure.play.json.writes

import domain.entities._
import play.api.libs.json.{Json, OWrites}

object EventSummaryWrites {
  implicit val attendantAssignedResource: OWrites[AttendantAssignedResource] = Json.writes[AttendantAssignedResource]
  implicit val resourceWrites: OWrites[Resource] = Json.writes[Resource]
  implicit val attendantWrites: OWrites[Attendant] = Json.writes[Attendant]
  implicit val eventSummaryWrites: OWrites[EventSummary] = Json.writes[EventSummary]
}
