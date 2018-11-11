package infrastructure.slick.entities

import slick.jdbc.MySQLProfile.api._

case class Input(id: Int = 0, event_id: Int, name: String, description: Option[String] = None, price: Float)

class InputTable(tag: Tag) extends Table[Input](tag, "input"){

  // PK
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  // FK
  def event_id = column[Int]("event_id")
  def event = foreignKey("event_fk", event_id, eventTable)(_.id)

  // Fields
  def name = column[String]("name")
  def description = column[Option[String]]("description")
  def price = column[Float]("price")

  def * = (id, event_id, name, description, price) <> (Input.tupled, Input.unapply)
  val eventTable = TableQuery[EventTable]
}
