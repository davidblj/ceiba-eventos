package infrastructure.mysql.entities

import slick.jdbc.MySQLProfile.api._

case class Input_obj(id: Int, event_id: Int, name: String, description: Option[String], price: Float)

class Input(tag: Tag) extends Table[Input_obj](tag, "input"){

  // PK
  def id = column[Int]("id", O.PrimaryKey)

  // FK
  def event_id = column[Int]("event_id")
  def event = foreignKey("event_fk", event_id, eventTable)(_.id)

  // Fields
  def name = column[String]("name")
  def description = column[String]("description")
  def price = column[Float]("price")

  def * = (id, event_id, name, description.?, price) <> (Input_obj.tupled, Input_obj.unapply)
  val eventTable = TableQuery[Event]
}
