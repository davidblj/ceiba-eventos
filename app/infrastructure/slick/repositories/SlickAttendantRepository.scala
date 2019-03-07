package infrastructure.slick.repositories

import domain.models.Attendant
import domain.repositories.AttendantRepository
import infrastructure.slick.entities.AttendantTable
import infrastructure.slick.transformers.AttendantTransformer
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class SlickAttendantRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider)
                                         (implicit ec: ExecutionContext)
                                         extends HasDatabaseConfigProvider[JdbcProfile] with AttendantRepository {
  import profile.api._
  
  val attendantTable = TableQuery[AttendantTable]

  def getBy(attendantName: String): Future[List[Attendant]] = {

    val query = attendantTable.filter(_.fullName like s"%attendantName%").result
    db.run(query).map(attendants => AttendantTransformer.toDomainObjectList(attendants))
  }
}
