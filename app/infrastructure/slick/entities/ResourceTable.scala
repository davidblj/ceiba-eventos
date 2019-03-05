package infrastructure.slick.entities

import slick.jdbc.MySQLProfile.api._

case class Resource(id: Int = 0, event_id: Int, name: String, description: Option[String] = None, price: Float,
                    stock: Option[Int] = None, quantity: Option[Int] = Some(0))

class ResourceTable(tag: Tag) extends Table[Resource](tag, "resource"){

  // PK
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  // FK
  def event_id = column[Int]("event_id")
  def event = foreignKey("event_fk", event_id, eventTable)(_.id)

  // Fields
  def name = column[String]("name")
  def description = column[Option[String]]("description")
  def price = column[Float]("price")
  def stock = column[Option[Int]]("stock")
  def quantity = column[Option[Int]]("quantity")

  def * = (id, event_id, name, description, price, stock, quantity) <>
                             (Resource.tupled, Resource.unapply)
  val eventTable = TableQuery[EventTable]
}
