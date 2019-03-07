package infrastructure.slick.entities

import slick.jdbc.MySQLProfile.api._

case class Attendant(id: Int = 0, fullName: String)

class AttendantTable(tag: Tag) extends Table[Attendant](tag, "attendant"){

  // PK
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  // Fields
  def fullName = column[String]("full_name")

  def * = (id, fullName) <> (Attendant.tupled, Attendant.unapply)
}
