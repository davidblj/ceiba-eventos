package application.transformers

import application.transfer_objects.{Attendant => AttendantAppObject}
import domain.models.{Attendant, AttendantAssignedResource}

object AttendantTransformer {

  def toDomainObject(attendant: AttendantAppObject): Attendant = {

    val domainAttendantAssignedResourceObject = attendant.assignedResources.map(assignedResource => {
      AttendantAssignedResource(assignedResource.resourceId, assignedResource.sharedAmount, None, None)
    })

    Attendant(attendant.eventId, None, attendant.employeeId, domainAttendantAssignedResourceObject, None)
  }
}
