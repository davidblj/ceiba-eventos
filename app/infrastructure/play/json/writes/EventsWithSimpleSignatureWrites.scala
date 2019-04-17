package infrastructure.play.json.writes

import application.transfer_objects.OutcomingEvent
import play.api.libs.json.{Json, Writes}

object EventsWithSimpleSignatureWrites {
  implicit val eventWrites: Writes[OutcomingEvent] = Json.writes[OutcomingEvent]
}
