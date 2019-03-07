package infrastructure.slick.transformers

import domain.models.Attendant
import infrastructure.slick.entities.{Attendant => AttendantTableObject}

object AttendantTransformer {

  def toTableObject(attendant: Attendant): Unit = {
    AttendantTableObject(fullName = attendant.fullName)
  }

  def toDomainObjectList(attendants: Seq[AttendantTableObject]): List[Attendant] = {
    attendants.map(attendant => toDomainObject(attendant)).toList
  }

  def toDomainObject(attendant: AttendantTableObject): Attendant = {
    Attendant(attendant.fullName, List(), Some(attendant.id))
  }
}
