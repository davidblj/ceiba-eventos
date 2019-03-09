package infrastructure.slick.transformers

import domain.models.Employee
import infrastructure.slick.entities.{Employee => AttendantTableObject}

object EmployeeTransformer {

  def toTableObject(attendant: Employee): Unit = {
    AttendantTableObject(fullName = attendant.fullName)
  }

  def toDomainObjectList(attendants: Seq[AttendantTableObject]): List[Employee] = {
    attendants.map(attendant => toDomainObject(attendant)).toList
  }

  def toDomainObject(attendant: AttendantTableObject): Employee = {
    Employee(attendant.fullName, Some(attendant.id))
  }
}
