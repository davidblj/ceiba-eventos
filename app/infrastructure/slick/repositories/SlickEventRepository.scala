package infrastructure.slick.repositories

import domain.entities.{Attendant, Event, EventSummary, Input, Resource}
import domain.aggregates.EventRepository
import domain.value_objects.{Location, ResourceSharedAmount}
import infrastructure.slick.entities
import infrastructure.slick.entities._
import infrastructure.slick.transformers._
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import infrastructure.slick.shared.CustomTypesTransformers.parse

import scala.concurrent.{ExecutionContext, Future}

class SlickEventRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider,
                                      val slickResourceRepository: SlickResourceRepository,
                                      val slickAttendantRepository: SlickAttendantRepository)
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

    for {
      event <- createEvent(event)
      _     <- slickResourceRepository.add(event.resources, event.eventId.get)
      _     <- insertInputsHandler(event.inputs, event.eventId.get)
    } yield event.eventId.get
  }

  override def add(location: Location): Future[Int] = {

    // todo: move this method into the location and event location repo
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
      _          <- insertEventLocation(locationId)
    } yield locationId
  }

  override def add(attendant: Attendant): Future[Int] = {
    slickAttendantRepository.add(attendant)
  }

  override def getBy(id: Int): Future[Event] = {

    def getBasicEventInformation: Future[entities.Event] = {
      val query = eventTable.filter(_.id === id).result.head
      db.run(query)
    }

    for {
      basicEventInformation <- getBasicEventInformation
      resources             <- slickResourceRepository.getAllResourcesBy(id)
    } yield EventTransformer.toSimpleDomainObject(basicEventInformation, Some(resources), None)
  }

  override def getAllAndSimplifyBy(finishedStatus: Boolean): Future[List[Event]] = {
    val query = eventTable.filter(_.finished === finishedStatus).result
    db.run(query).map(events => EventTransformer.toSimpleDomainObjectList(events))
  }

  override def updateFinishedStatusIn(eventId: Int): Future[Int] = {

    val query = eventTable.filter(_.id === eventId)
                          .map(_.finished)
    val updateQuery = query.update(true)
    db.run(updateQuery)
  }

  override def getSummaryBy(id: Int): Future[EventSummary] = {

    for {
      resources  <- slickResourceRepository.getAllResourcesBy(id)
      event      <- getBy(id)
      attendants <- slickAttendantRepository.getAttendantsBy(id)
    } yield EventSummary(event.eventId.get, event.name, parse(event.insertionDate), resources.toList, attendants)
  }

  override def getResourceBy(resourceId: Int): Future[Resource] = {
    slickResourceRepository.getBy(resourceId)
  }

  override def set(resourceQuantityAmount: ResourceSharedAmount): Future[Int] = {
    slickResourceRepository.set(resourceQuantityAmount)
  }

  // todo: use an input repo, and use it through this class
  def insertInputs(inputs: List[Input], eventId: Int): Future[Any] = {

    val inputTableSeq = InputTransformer.toTableObjectList(inputs, eventId)
    val query = inputTable ++= inputTableSeq
    db.run(query)
  }
}
