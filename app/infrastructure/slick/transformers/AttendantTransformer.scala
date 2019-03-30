package infrastructure.slick.transformers

import infrastructure.slick.entities.{Attendant => AttendantTableObject}
import domain.models.Attendant

object AttendantTransformer {

  def toTableObject(attendant: Attendant): AttendantTableObject = {

    AttendantTableObject(event_id = attendant.eventId, employee_id = attendant.employeeId,
                        location_id = attendant.locationId)
  }

  /*def toDomainObject(attendantTableObject: AttendantTableObject) = {
    Attendant()
  }*/
}
