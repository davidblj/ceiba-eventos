package domain.entities

case class EventSummary(name: String, insertionDate: String, resources: List[Resource], attendants: List[Attendant]) { }
