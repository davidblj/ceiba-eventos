package application.transfer_objects

case class OutcomingEvent(name: String,
                          insertionDate: String,
                          eventId: Int,
                          status: Boolean = false)
