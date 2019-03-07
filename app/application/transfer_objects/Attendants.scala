package application.transfer_objects

import domain.models.{Attendant => AttendantDomainObject}

case class Attendants(attendants: List[AttendantDomainObject])
