package domain

import domain.models.{Event, Resource}
import domain.core.Organizer
import builders.EventBuilder
import domain.repositories.{EventRepository, LocationRepository}
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
      val mockedLocationRepository = mock[LocationRepository]
      when(mockedEventRepository.add(event)) thenReturn Future.successful(1)

      // act
      val organizer = new Organizer(mockedEventRepository, mockedLocationRepository)
      val result: Future[Int] = organizer.launch(event)

      // assert
      result map { code => code mustBe 2}
    }

    "failed gracefully to launch an event with a non-existing favorite resource in the resource list" in {

      assertThrows[IllegalArgumentException] {

        val nonExistingResource = "beer pack"
        val resources = List(Resource("bottles of whiskey", 9.9f, stock = Some(50)))
        new EventBuilder()
          .withFavoriteResource(nonExistingResource)
          .withResources(resources)
          .build()
      }
    }
  }
}