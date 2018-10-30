package infrastructure.mysql.entities

import slick.jdbc.MySQLProfile.api._

case class Event_obj(id: Int, name: String, description: Option[String])

class Event(tag: Tag) extends Table[Event_obj](tag, "event"){

  // PK
  def id = column[Int]("id", O.PrimaryKey)

  // Fields
  def name = column[String]("name")
  def description = column[String]("description")

  def * = (id, name, description.?) <> (Event_obj.tupled, Event_obj.unapply)
}
