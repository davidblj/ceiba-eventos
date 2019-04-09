package domain.entities

case class Attendant(eventId: Int, employee: Option[String], employeeId: Int, location: Option[String], locationId: Int,
                     assignedResources: List[AttendantAssignedResource], attendantId: Option[Int],
                     insertionDate: Option[String]) {

  def setId(id: Int): Attendant = {
    Attendant(eventId, employee, employeeId, location, locationId, assignedResources, Some(id), insertionDate)
  }
}
