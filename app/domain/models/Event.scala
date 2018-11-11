package domain.models

case class Event(name: String,
                 description: String,
                 id: Option[Int] = None,
                 resources: Option[Resources] = None,
                 inputs: Option[Inputs] = None)