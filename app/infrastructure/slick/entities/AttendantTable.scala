package infrastructure.slick.entities

import org.joda.time.DateTime
import slick.jdbc.MySQLProfile.api._
import infrastructure.slick.shared.CustomColumnTypes._

case class Attendant(id: Int = 0, insertionDate: DateTime = DateTime.now(), event_id: Int, employee_id: Int, location_id: Int)

class AttendantTable(tag: Tag) extends Table[Attendant](tag, "attendant"){

  // PK
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  // Fields
  def insertionDate = column[DateTime]("insertion_date")

  // FK
  def event_id = column[Int]("event_id")
  def event = foreignKey("event_fk", event_id, eventTable)(_.id)

  def employee_id = column[Int]("employee_id")
  def employee = foreignKey("employee_fk", event_id, employeeTable)(_.id)

  def location_id = column[Int]("location_id")
  def location = foreignKey("location_fk", event_id, locationTable)(_.id)

  def * = (id, insertionDate, event_id, employee_id, location_id) <> (intoAttendant, Attendant.unapply)

  def intoAttendant(shape: (Int, DateTime, Int, Int, Int)): Attendant = {
    Attendant(shape._1, shape._2, shape._3, shape._4, shape._5)
  }

  // Tables
  val eventTable = TableQuery[EventTable]
  val employeeTable = TableQuery[EmployeeTable]
  val locationTable = TableQuery[LocationTable]
}
