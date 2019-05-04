package domain.aggregates

import scala.concurrent.Future

trait LocationRepository {
  def getAll: Future[Seq[String]]
}
