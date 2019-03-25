package infrastructure.slick.transformers

import domain.models.Employee
import infrastructure.slick.entities.{Employee => EmployeeTableObject}

object EmployeeTransformer {

  def toTableObject(employee: Employee): Unit = {
    EmployeeTableObject(fullName = employee.fullName)
  }

  def toDomainObjectList(employee: Seq[EmployeeTableObject]): List[Employee] = {
    employee.map(attendant => toDomainObject(attendant)).toList
  }

  def toDomainObject(employee: EmployeeTableObject): Employee = {
    Employee(employee.fullName, Some(employee.id))
  }
}
