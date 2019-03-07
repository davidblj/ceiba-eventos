package application.actions.attendant

import application.transfer_objects.Attendants
import domain.core.Organizer
import javax.inject.Inject

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class GetAttendantsByName @Inject() (organizer: Organizer)  {

  def execute(attendantName: String): Future[Attendants] = {
    organizer.getAttendantsBy(attendantName).map(attendants => Attendants(attendants))
  }
}
