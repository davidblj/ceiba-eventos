package infrastructure.slick.repositories

import domain.entities.AttendantAssignedResource
import infrastructure.slick.entities
import infrastructure.slick.entities.{AttendantAssignedResourceTable, ResourceTable}
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

  val resourceTable = TableQuery[ResourceTable]

  def add(attendantAssignedResources: List[AttendantAssignedResource], attendantId: Int): Future[Any] = {

    val attendantAssignedResourceSeq = AttendantAssignedResourceTransformer
                                       .toTableObjectList(attendantAssignedResources, attendantId)
    val query = attendantAssignedResourceTable ++= attendantAssignedResourceSeq
    db.run(query)
  }

  def getAllBy(attendantId: Int): Future[List[AttendantAssignedResource]] = {

    val query = attendantAssignedResourceTable.filter(_.attendant_id === attendantId).result
    db.run(query).map(attendantsAssignedResources =>
      AttendantAssignedResourceTransformer.toDomainObjectList(attendantsAssignedResources.toList
    ))
  }

  def getByAttendantIdAndEventId(attendantId: Int): Future[Seq[AttendantAssignedResource]] = {

    val query = attendantAssignedResourceTable join resourceTable on (_.resource_id === _.id)
    db.run(query.result)
      .map(assignedResources => assignedResources
      .map { case(assignedResource, resource) =>
        AttendantAssignedResource(assignedResource.resource_id, Some(resource.name), assignedResource.shared_amount,
                                  Some(assignedResource.attendant_id), Some(assignedResource.id))
      })
  }
}
