package application.transfer_objects

case class Attendant(eventId: Int, employeeId: Int, assignedResources: List[AttendantAssignedResource])
