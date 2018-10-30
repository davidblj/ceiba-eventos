package infrastructure.mysql.entities

import slick.jdbc.MySQLProfile.api._

case class Resource_obj(id: Int, event_id: Int, name: String, description: Option[String], price: Float,
                        stock: Option[Int])

class Resource(tag: Tag) extends Table[Resource_obj](tag, "resource"){

  // PK
  def id = column[Int]("id", O.PrimaryKey)

  // FK
  def event_id = column[Int]("event_id")
  def event = foreignKey("event_fk", event_id, eventTable)(_.id)

  // Fields
  def name = column[String]("name")
  def description = column[String]("description")
  def price = column[Float]("price")
  def stock = column[Int]("stock")

  def * = (id, event_id, name, description.?, price, stock.?) <> (Resource_obj.tupled, Resource_obj.unapply)
  val eventTable = TableQuery[Event]
}
