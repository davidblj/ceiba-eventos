package domain.models

case class AttendantAssignedResource(resourceId: Int, sharedAmount: Int, attendantId: Option[Int],
                                     attendantAssignedResourceId: Option[Int])

