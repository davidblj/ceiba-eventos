package domain.repositories

import domain.models.Attendant

import scala.concurrent.Future

trait AttendantRepository {
    def getBy(attendantName: String): Future[List[Attendant]]
}
