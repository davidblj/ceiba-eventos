package infrastructure.slick.repositories

import domain.models.AttendantAssignedResource
import infrastructure.slick.entities.AttendantAssignedResourceTable
import infrastructure.slick.transformers.AttendantAssignedResourceTransformer
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class SlickAttendantAssignedResourceRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider)
                                                         (implicit ec: ExecutionContext)
                                                         extends HasDatabaseConfigProvider[JdbcProfile]{

  import profile.api._

  val attendantAssignedResourceTable = TableQuery[AttendantAssignedResourceTable]

  def add(attendantAssignedResources: List[AttendantAssignedResource], attendantId: Int): Future[Any] = {

    val attendantAssignedResourceSeq = AttendantAssignedResourceTransformer
                                       .toTableObjectList(attendantAssignedResources, attendantId)
    val query = attendantAssignedResourceTable ++= attendantAssignedResourceSeq
    db.run(query)
  }
}
