package infrastructure.slick.entities

import slick.jdbc.MySQLProfile.api._

case class EventLocations(event_id: Int, location_id: Int)

class EventLocationsTable(tag: Tag) extends Table[EventLocations](tag, "event_locations"){

  // FK
  def event_id = column[Int]("event_id")
  def event = foreignKey("event_fk", event_id, eventTable)(_.id)

  def location_id = column[Int]("location_id")
  def location = foreignKey("location_fk", location_id, locationTable)(_.id)

  val eventTable = TableQuery[EventTable]
  val locationTable = TableQuery[LocationTable]

  def * = (event_id, location_id) <> (EventLocations.tupled, EventLocations.unapply)
}
