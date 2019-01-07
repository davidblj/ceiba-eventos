package infrastructure.slick.repositories

import domain.repositories.LocationRepository
import infrastructure.slick.entities.{LocationTable, Location}
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class SlickLocationRepository  @Inject() (val dbConfigProvider: DatabaseConfigProvider)
                                         (implicit ec: ExecutionContext)
                                         extends HasDatabaseConfigProvider[JdbcProfile] with LocationRepository {

  import profile.api._
  val locationTable = TableQuery[LocationTable]

  override def getAll: Future[Seq[String]] = {
    val query = locationTable.map(_.name).result
    db.run(query)
  }
}
