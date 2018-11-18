package domain

import domain.models.Event
import domain.core.Organizer
import builders.EventBuilder
import domain.repositories.EventRepository
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import org.mockito.Mockito._
import org.scalatest.concurrent.ScalaFutures
import scala.concurrent.{ExecutionContext, Future}

class OrganizerSpec extends PlaySpec with MockitoSugar with ScalaFutures {

  implicit val ec: ExecutionContext = ExecutionContext.Implicits.global

  "Organizer#launchEvent" should {
    "launch successfully a well constructed event" in {

      // arrange
      val event: Event = new EventBuilder().build()
      val mockedEventRepository = mock[EventRepository]
      when(mockedEventRepository.insertEvent(event)) thenReturn Future.successful(1)

      // act
      val organizer = new Organizer(mockedEventRepository)
      val launchEventFuture: Future[Int] = organizer.launchEvent(event)

      // assert
      launchEventFuture map { code => code mustBe 2}
    }
  }
}