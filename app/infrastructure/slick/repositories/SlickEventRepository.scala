package infrastructure.slick.repositories

import domain.data_containers.Location
import domain.models.{Event, Input, Resource}
import domain.repositories.EventRepository
import infrastructure.slick.entities.{EventLocationsTable, EventTable, InputTable, LocationTable, ResourceTable, Resource => ResourceTableObject}
import infrastructure.slick.transformers._
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}

class SlickEventRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider, val slickResourceRepository: SlickResourceRepository)
                                     (implicit ec: ExecutionContext)
                                     extends HasDatabaseConfigProvider[JdbcProfile] with EventRepository {

  import profile.api._

  val eventTable = TableQuery[EventTable]
  val resourceTable = TableQuery[ResourceTable]
  val inputTable = TableQuery[InputTable]
  val locationTable = TableQuery[LocationTable]
  val eventLocationsTable = TableQuery[EventLocationsTable]

  override def add(event: Event): Future[Int] = {

    def insertInputsHandler(inputs: Option[List[Input]], eventId: Int): Future[Any] = {

      if (inputs.isDefined) insertInputs(inputs.get, eventId)
      else Future.successful(None)
    }

    def createEvent(event: Event): Future[Event] = {

      val eventTableObject = EventTransformer.toTableObject(event)
      val query = eventTable returning eventTable.map(_.id) += eventTableObject
      db.run(query).map(id => event.setId(id))
    }

    // todo: handle slick errors
    for {
      event <- createEvent(event)
      _ <- insertResources(event.resources, event.eventId.get)
      _ <- insertInputsHandler(event.inputs, event.eventId.get)
    } yield event.eventId.get
  }

  override def addLocation(location: Location): Future[Int] = {

    // todo: move this method into the location repo
    def insertLocation(): Future[Int] = {

      val locationTableObject = LocationTransformer.toTableObject(location.name)
      val addLocationQuery = locationTable returning locationTable.map(_.id) += locationTableObject
      db.run(addLocationQuery)
    }

    def insertEventLocation(locationId: Int): Future[Int] = {

      val eventLocationTableObject = EventLocationsTransformer.toTableObject(location.eventId, locationId)
      val addEventLocationsQuery = eventLocationsTable += eventLocationTableObject
      db.run(addEventLocationsQuery)
    }

    // todo: handle slick errors
    for {
      locationId <- insertLocation()
      _ <- insertEventLocation(locationId)
    } yield locationId
  }

  override def getResourcesBy(eventId: Int): Future[Seq[Resource]] = {
    slickResourceRepository.getAllByEventId(eventId)
  }

  // todo: use a resource and an input repo, but use it through this class
  def insertResources(resources: List[Resource], eventId: Int): Future[Any] = {

    val resourceTableSeq: Seq[ResourceTableObject] = ResourceTransformer.toTableObjectList(resources, eventId)
    val query = resourceTable ++= resourceTableSeq
    db.run(query)
  }

  def insertInputs(inputs: List[Input], eventId: Int): Future[Any] = {

    val inputTableSeq = InputTransformer.toTableObjectList(inputs, eventId)
    val query = inputTable ++= inputTableSeq
    db.run(query)
  }
}