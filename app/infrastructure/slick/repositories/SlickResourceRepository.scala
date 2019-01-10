package infrastructure.slick.repositories

import domain.models.Resource
import infrastructure.slick.entities.{Resource => InfraResource, ResourceTable}
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class SlickResourceRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider)
                                        (implicit ec: ExecutionContext)
                                        extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._
  val resourceTable = TableQuery[ResourceTable]

  def getAllByEventId(eventId: Int): Future[Seq[Resource]] = {

    // todo: move it into the transformers
    def resourceToDomain(resources: Seq[InfraResource]): Seq[Resource] = {
      resources.map(resource => Resource(resource.name, resource.price, resource.description, resource.stock))
    }

    val query = resourceTable.filter(_.event_id === eventId).result
    db.run(query).map(resources => resourceToDomain(resources))
  }
}