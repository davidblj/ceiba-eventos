package domain.entities

case class EventSummary(id: Int, name: String, insertionDate: String, resources: List[Resource],
                        attendants: List[Attendant]) { }
