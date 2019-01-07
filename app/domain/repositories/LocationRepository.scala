package domain.repositories

import scala.concurrent.Future

trait LocationRepository {
  def getAll: Future[Seq[String]]
}
