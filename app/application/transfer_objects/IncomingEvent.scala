package application.transfer_objects

case class IncomingEvent(name: String,
                         resources: List[Resource],
                         favoriteResource: Option[String],
                         description: Option[String],
                         inputs: Option[List[Input]])
