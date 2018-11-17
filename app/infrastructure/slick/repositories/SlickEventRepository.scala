package infrastructure.slick.repositories

import domain.models.{Event, Input, Resource}
import domain.repositories.EventRepository
import infrastructure.slick.entities.{EventTable, InputTable, ResourceTable, Event => EventTableObject, Resource => ResourceTableObject}
import infrastructure.slick.transformers.{InputTransformer, ResourceTransformer}
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class SlickEventRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider)
                                     (implicit ec: ExecutionContext)
                                     extends HasDatabaseConfigProvider[JdbcProfile] with EventRepository {

  import profile.api._
  val eventTable = TableQuery[EventTable]
  val resourceTable = TableQuery[ResourceTable]
  val inputTable = TableQuery[InputTable]

  @Override def insertEvent(event: Event): Future[Int] = {

    def insertInputsHandler(inputs: Option[List[Input]]): Future[Any] = {

      if (inputs.isDefined) insertInputs(inputs.get)
      else Future.successful(None)
    }

    def createEvent(event: Event): Future[Event] = {

      val eventTableObject = EventTableObject(name = event.name, description = event.description)
      val query = eventTable returning eventTable.map(e => e.id) += eventTableObject
      db.run(query).map(id => event.setId(id))
    }

    for {
      event <- createEvent(event)
      _ <- insertResources(event.resources)
      _ <- insertInputsHandler(event.inputs)
    } yield event.eventId.get
  }

  def insertResources(resources: List[Resource]): Future[Any] = {

    // todo: validate the event id
    val resourceTableSeq: Seq[ResourceTableObject] = ResourceTransformer.toTableObjectList(resources)
    val query = resourceTable ++= resourceTableSeq
    db.run(query)
  }

  def insertInputs(inputs: List[Input]): Future[Any] = {

    // todo: validate the event id
    val inputTableSeq = InputTransformer.toTableObjectList(inputs)
    val query = inputTable ++= inputTableSeq
    db.run(query)
  }
}