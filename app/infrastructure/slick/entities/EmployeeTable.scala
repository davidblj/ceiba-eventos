package infrastructure.slick.entities

import slick.jdbc.MySQLProfile.api._

case class Employee(id: Int = 0, fullName: String)

class EmployeeTable(tag: Tag) extends Table[Employee](tag, "employee"){

  // PK
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  // Fields
  def fullName = column[String]("full_name")

  def * = (id, fullName) <> (Employee.tupled, Employee.unapply)
}
