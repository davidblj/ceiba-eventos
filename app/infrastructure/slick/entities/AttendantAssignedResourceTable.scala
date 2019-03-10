package infrastructure.slick.entities

import slick.jdbc.MySQLProfile.api._

case class AttendantAssignedResource(id: Int = 0, attendant_id: Int, resource_id: Int, shared_amount: Int)

class AttendantAssignedResourceTable(tag: Tag) extends Table[AttendantAssignedResource](tag, "attendant_assigned_resource") {

  // PK
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  // FK
  def attendant_id = column[Int]("attendant_id")
  def attendant = foreignKey("attendant_fk", attendant_id, attendantTable)(_.id)

  def resource_id = column[Int]("resource_id")
  def resource = foreignKey("resource_fk", attendant_id, resourceTable)(_.id)

  // Fields
  def shared_amount = column[Int]("shared_amount")

  def * = (id, attendant_id, resource_id, shared_amount) <>
    (AttendantAssignedResource.tupled, AttendantAssignedResource.unapply)

  // Tables
  val attendantTable = TableQuery[AttendantTable]
  val resourceTable = TableQuery[ResourceTable]
}
