package infrastructure.slick.entities

import slick.jdbc.MySQLProfile.api._

case class Attendant(id: Int = 0, event_id: Int, employee_id: Int)

class AttendantTable(tag: Tag) extends Table[Attendant](tag, "attendant"){

  // PK
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  // FK
  def event_id = column[Int]("event_id")
  def event = foreignKey("event_fk", event_id, eventTable)(_.id)

  def employee_id = column[Int]("employee_id")
  def employee = foreignKey("employee_fk", event_id, employeeTable)(_.id)

  def * = (id, event_id, employee_id) <> (Attendant.tupled, Attendant.unapply)

  // Tables
  val eventTable = TableQuery[EventTable]
  val employeeTable = TableQuery[EmployeeTable]
}
