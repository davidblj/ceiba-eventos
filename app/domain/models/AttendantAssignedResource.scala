package domain.models

case class AttendantAssignedResource(resourceId: Int, resourceName: Option[String], sharedAmount: Int, attendantId: Option[Int],
                                     attendantAssignedResourceId: Option[Int])
