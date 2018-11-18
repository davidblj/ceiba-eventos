package infrastructure.slick.entities

import slick.jdbc.MySQLProfile.api._

case class Event(id: Int = 0, name: String, description: Option[String] = None, favoriteResource: Option[String] = None)

class EventTable(tag: Tag) extends Table[Event](tag, "event"){

  // PK
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  // Fields
  def name = column[String]("name")
  def description = column[Option[String]]("description")
  def favoriteResource = column[Option[String]]("favorite_resource")

  def * = (id, name, description, favoriteResource) <> (Event.tupled, Event.unapply)
}
