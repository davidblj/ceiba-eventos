package domain.value_objects

case class AssignedResource(resourceId: Int, assignedAmount: Int, attendantId: Option[Int]) { }
