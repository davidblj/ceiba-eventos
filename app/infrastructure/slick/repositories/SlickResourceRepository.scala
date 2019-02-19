package infrastructure.slick.repositories

import domain.models.Resource
import infrastructure.slick.entities.{ResourceTable, Resource => ResourceTableObject}
import infrastructure.slick.transformers.ResourceTransformer
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class SlickResourceRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider)
                                        (implicit ec: ExecutionContext)
                                        extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._
  val resourceTable = TableQuery[ResourceTable]

  def add(resources: List[Resource], eventId: Int): Future[Any] = {

    val resourceTableSeq: Seq[ResourceTableObject] = ResourceTransformer.toTableObjectList(resources, eventId)
    val query = resourceTable ++= resourceTableSeq
    db.run(query)
  }

  def getAllByEventId(eventId: Int): Future[Seq[Resource]] = {

    val query = resourceTable.filter(_.event_id === eventId).result
    db.run(query).map(resources => ResourceTransformer.toDomainObjectList(resources))
  }
}