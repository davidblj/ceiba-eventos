package infrastructure.slick.entities

import slick.jdbc.MySQLProfile.api._

case class Location(id: Int = 0, name: String)

class LocationTable(tag: Tag) extends Table[Location](tag, "location"){

  // PK
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  // Fields
  def name = column[String]("name")

  def * = (id, name) <> (Location.tupled, Location.unapply)
}
