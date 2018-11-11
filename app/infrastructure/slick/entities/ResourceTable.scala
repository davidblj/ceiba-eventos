package infrastructure.slick.entities

import slick.jdbc.MySQLProfile.api._

case class Resource(id: Int = 0, event_id: Int, name: String, description: Option[String] = None, price: Float,
                    stock: Option[Int] = None)

class ResourceTable(tag: Tag) extends Table[Resource](tag, "resource"){

  // PK
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  // FK7
  def event_id = column[Int]("event_id")
  def event = foreignKey("event_fk", event_id, eventTable)(_.id)

  // Fields
  def name = column[String]("name")
  def description = column[Option[String]]("description")
  def price = column[Float]("price")
  def stock = column[Option[Int]]("stock")

  def * = (id, event_id, name, description, price, stock) <> (Resource.tupled, Resource.unapply)
  val eventTable = TableQuery[EventTable]
}
