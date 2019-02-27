package infrastructure.slick.repositories

import domain.models.Resource
import domain.value_objects.ResourceStock
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

  def getAllResourcesBy(eventId: Int): Future[Seq[Resource]] = {

    val query = resourceTable.filter(_.event_id === eventId).result
    db.run(query).map(resources => ResourceTransformer.toDomainObjectList(resources))
  }

  def getBy(resourceId: Int): Future[Resource] = {

    val query = resourceTable.filter(_.id === resourceId).result.head
    db.run(query).map(resource => ResourceTransformer.toDomainObject(resource))
  }

  def set(resourceStock: ResourceStock): Future[Int] = {

    val query = resourceTable.filter(_.id === resourceStock.id)
                             .map(_.stock)
    val updateQuery = query.update(Some(resourceStock.amount))
    db.run(updateQuery)
  }
}
