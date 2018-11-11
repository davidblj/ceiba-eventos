package domain.models

case class Event(name: String,
                 description: Option[String] = None,
                 resources: Option[List[Resource]] = None,
                 inputs: Option[List[Input]] = None,
                 id: Int = 0)