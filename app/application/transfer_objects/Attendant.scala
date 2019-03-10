package application.transfer_objects

case class Attendant(eventId: Int, employee: String, employeeId: Int,
                     assignedResources: List[AttendantAssignedResource])
