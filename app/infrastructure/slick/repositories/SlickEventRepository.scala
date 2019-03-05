package infrastructure.slick.repositories

import domain.models.{Event, Input, Resource}
import domain.repositories.EventRepository
import domain.value_objects.{Location, ResourceQuantityAmount}
import infrastructure.slick.entities
import infrastructure.slick.entities._
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

  override def getBy(id: Int): Future[Event] = {

    // todo: attach its inputs
    def getBasicEventInformation: Future[entities.Event] = {
      val query = eventTable.result.head
      db.run(query)
    }

    for {
      basicEventInformation <- getBasicEventInformation
      resources <- getResourcesBy(id)
    } yield EventTransformer.toDomainObject(basicEventInformation, resources)
  }

  override def getResourceBy(resourceId: Int): Future[Resource] = {
    slickResourceRepository.getBy(resourceId)
  }

  override def set(resourceQuantityAmount: ResourceQuantityAmount): Future[Int] = {
    slickResourceRepository.set(resourceQuantityAmount)
  }

  def getResourcesBy(eventId: Int): Future[Seq[Resource]] = {
    slickResourceRepository.getAllResourcesBy(eventId)
  }

  def insertResources(resources: List[Resource], eventId: Int): Future[Any] = {
    slickResourceRepository.add(resources, eventId)
  }

  def insertInputs(inputs: List[Input], eventId: Int): Future[Any] = {

    // todo: use an input repo, and use it through this class
    val inputTableSeq = InputTransformer.toTableObjectList(inputs, eventId)
    val query = inputTable ++= inputTableSeq
    db.run(query)
  }
}