package domain.models

import domain.value_objects.AssignedResource

case class Attendant(fullName: String, assignedResources: List[AssignedResource], id: Option[Int]) { }
