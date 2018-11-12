package infrastructure.slick.repositories

import domain.models.{Event, Input, Resource}
import domain.repositories.EventRepository
import infrastructure.slick.entities.{EventTable, InputTable, ResourceTable, Event => EventTableObject, Resource => ResourceTableObject}
import infrastructure.slick.transformers.{InputTransformer, ResourceTransformer}
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

// todo: insertResource and insertInput, should not use option values
class SlickEventRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider)
                                     (implicit ec: ExecutionContext)
                                     extends HasDatabaseConfigProvider[JdbcProfile] with EventRepository {

  import profile.api._
  val eventTable = TableQuery[EventTable]
  val resourceTable = TableQuery[ResourceTable]
  val inputTable = TableQuery[InputTable]

  @Override def createEvent(event: Event): Future[Int] = {

    for {
      event <- insertEvent(event)
      _ <- insertResources(event.resources)
      _ <- insertInputs(event.inputs)
    } yield event.eventId.get
  }

  private def insertEvent(event: Event): Future[Event] = {
    val query = eventTable returning eventTable.map(e => e.id) += EventTableObject(name = event.name, description = event.description)
    db.run(query).map(id => event.setId(id))
  }

  private def insertResources(resources: Option[List[Resource]]): Future[Any] = {

    // todo: validate the event id
    if (resources.isDefined) {

      val resourceTableSeq: Seq[ResourceTableObject] = ResourceTransformer.toTableObjectList(resources.get)
      val query = resourceTable ++= resourceTableSeq
      db.run(query)

    } else Future { 0 }
  }

  private def insertInputs(inputs: Option[List[Input]]): Future[Any] = {

    // todo: validate the event id
    if (inputs.isDefined) {

      val inputTableSeq = InputTransformer.toTableObjectList(inputs.get)
      val query = inputTable ++= inputTableSeq
      db.run(query)

    } else Future { 0 }
  }
}