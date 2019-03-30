package infrastructure.slick.transformers

import domain.models.AttendantAssignedResource
import infrastructure.slick.entities.{AttendantAssignedResource => AttendantAssignedResourceTableObject}

object AttendantAssignedResourceTransformer {

  def toTableObject(attendantAssignedResource: AttendantAssignedResource, attendantId: Int)
                   : AttendantAssignedResourceTableObject = {

    AttendantAssignedResourceTableObject(attendant_id = attendantId,
                                         resource_id = attendantAssignedResource.resourceId,
                                         shared_amount = attendantAssignedResource.sharedAmount)
  }

  def toTableObjectList(attendantAssignedResources: List[AttendantAssignedResource], attendantId: Int)
                       : List[AttendantAssignedResourceTableObject] = {

    attendantAssignedResources.map(attendantAssignedResource => toTableObject(attendantAssignedResource, attendantId))
  }

  def toDomainObject(attendantAssignedResourceTableObject: AttendantAssignedResourceTableObject)
                        : AttendantAssignedResource = {

    AttendantAssignedResource(
      attendantAssignedResourceTableObject.resource_id,
      None,
      attendantAssignedResourceTableObject.shared_amount,
      Some(attendantAssignedResourceTableObject.attendant_id),
      Some(attendantAssignedResourceTableObject.id))
  }

  def toDomainObjectList(attendantAssignedResourceTableObjectList: List[AttendantAssignedResourceTableObject])
                        : List[AttendantAssignedResource] = {

    attendantAssignedResourceTableObjectList.map(attendantAssignedResource => toDomainObject(attendantAssignedResource))
  }
}
