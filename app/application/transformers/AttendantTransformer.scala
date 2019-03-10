package application.transformers

import application.transfer_objects.{Attendant => AttendantAppObject}
import domain.models.{Attendant, AttendantAssignedResource}

object AttendantTransformer {

  def applicationToDomainObject(attendant: AttendantAppObject): Attendant = {

    val domainAttendantAssignedResourceObject = attendant.assignedResources.map(assignedResource => {
      AttendantAssignedResource(assignedResource.resourceId, assignedResource.sharedAmount, None, None)
    })

    Attendant(attendant.eventId, attendant.employee, attendant.employeeId, domainAttendantAssignedResourceObject, None)
  }
}
