package infrastructure.slick.repositories

import domain.models.Attendant
import infrastructure.slick.entities.AttendantTable
import infrastructure.slick.transformers._
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class SlickAttendantRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider,
                                          val attendantAssignedResourceRepository: SlickAttendantAssignedResourceRepository)
                                         (implicit ec: ExecutionContext)
                                         extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val attendantTable = TableQuery[AttendantTable]

  def add(attendant: Attendant): Future[Int] = {

    def createAttendant(): Future[Attendant] = {

      val attendantTableObject = AttendantTransformer.toTableObject(attendant)
      val query = attendantTable returning attendantTable.map(_.id) += attendantTableObject
      db.run(query).map(id => attendant.setId(id))
    }

    for {
      attendant <- createAttendant()
      _         <- attendantAssignedResourceRepository.add(attendant.assignedResources, attendant.attendantId.get)
    } yield attendant.attendantId.get
  }
}
