package infrastructure.play.json.writes

import domain.entities._
import play.api.libs.json.{Json, Writes}

object EventSummaryWrites {
  implicit val attendantAssignedResource: Writes[AttendantAssignedResource] = Json.writes[AttendantAssignedResource]
  implicit val resourceWrites: Writes[Resource] = Json.writes[Resource]
  implicit val attendantWrites: Writes[Attendant] = Json.writes[Attendant]
  implicit val eventSummaryWrites: Writes[EventSummary] = Json.writes[EventSummary]
}
