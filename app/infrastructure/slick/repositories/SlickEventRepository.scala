package infrastructure.slick.repositories

import domain.models.{Event, Input, Resource}
import domain.repositories.EventRepository
import infrastructure.slick.entities.EventTable
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class SlickEventRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider)
                                     (implicit ec: ExecutionContext)
                                     extends HasDatabaseConfigProvider[JdbcProfile] with EventRepository {

  import profile.api._
  val events = TableQuery[EventTable]

  override def createEvent(event: Event): Future[Int] = {
      insertEvent(event)
  }

  def insertEvent(event: Event): Future[Int] = {
    val query = events.map(e => (e.name, e.description)) += (event.name, event.description)
    db.run(query)
  }

  /* def insertResources(resources: Resource) = {}
     def insertInput(inputs: Input) = {} */
}