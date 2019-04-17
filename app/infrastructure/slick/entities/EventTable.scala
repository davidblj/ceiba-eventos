package infrastructure.slick.entities

import org.joda.time.DateTime
import slick.jdbc.MySQLProfile.api._
import infrastructure.slick.shared.CustomColumnTypes._

case class Event(id: Int = 0, insertionDate: DateTime = DateTime.now(), name: String, description: Option[String] = None,
                 favoriteResource: Option[String] = None, finished: Boolean = false)

class EventTable(tag: Tag) extends Table[Event](tag, "event"){

  // PK
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  // Fields
  def insertionDate = column[DateTime]("insertion_date")
  def name = column[String]("name")
  def description = column[Option[String]]("description")
  def favoriteResource = column[Option[String]]("favorite_resource")
  def finished = column[Boolean]("finished")

  def * = (id, insertionDate, name, description, favoriteResource, finished) <> (Event.tupled, Event.unapply)
}
