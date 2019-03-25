package application.actions.attendants

import application.transfer_objects.Attendant
import domain.core.Organizer
import application.transformers.AttendantTransformer
import javax.inject.Inject

import scala.concurrent.Future

class SignUpAttendant @Inject()(organizer: Organizer) {

  def execute(attendant: Attendant): Future[Int] = {

    val domainAttendantObject = AttendantTransformer.toDomainObject(attendant)
    organizer.signUp(domainAttendantObject)
  }
}
