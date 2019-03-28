package domain.models

// todo: add its resource name
case class AttendantAssignedResource(resourceId: Int, sharedAmount: Int, attendantId: Option[Int],
                                     attendantAssignedResourceId: Option[Int])
