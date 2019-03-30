package application.transfer_objects

case class Attendant(eventId: Int, employeeId: Int, locationId: Int, assignedResources: List[AttendantAssignedResource])
