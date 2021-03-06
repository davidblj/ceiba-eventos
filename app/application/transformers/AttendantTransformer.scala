package application.transformers

import application.transfer_objects.{Attendant => AttendantAppObject}
import domain.entities.{Attendant, AttendantAssignedResource}

object AttendantTransformer {

  def toDomainObject(attendant: AttendantAppObject): Attendant = {

    val domainAttendantAssignedResourceObject = attendant.assignedResources.map(assignedResource => {
      AttendantAssignedResource(assignedResource.resourceId, None, assignedResource.sharedAmount, None, None)
    })

    Attendant(attendant.eventId, None, attendant.employeeId, None, attendant.locationId,
              domainAttendantAssignedResourceObject, None, None)
  }
}
