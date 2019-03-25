package infrastructure.slick.transformers

import infrastructure.slick.entities.{Attendant => AttendantTableObject}
import domain.models.Attendant

object AttendantTransformer {

  def toTableObject(attendant: Attendant) = {
    AttendantTableObject(event_id = attendant.eventId, employee_id = attendant.employeeId)
  }

  /*def toDomainObject(attendantTableObject: AttendantTableObject) = {
    Attendant()
  }*/
}
