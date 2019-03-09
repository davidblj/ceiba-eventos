package application.transfer_objects

import domain.models.{Employee => EmployeeDomainObject}

case class Employees(employees: List[EmployeeDomainObject])
