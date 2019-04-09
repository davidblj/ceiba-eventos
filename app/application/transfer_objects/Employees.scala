package application.transfer_objects

import domain.entities.{Employee => EmployeeDomainObject}

case class Employees(employees: List[EmployeeDomainObject])
