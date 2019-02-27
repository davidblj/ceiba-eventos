package domain.core

import domain.models.{Event, Resource}
import domain.repositories.{EventRepository, LocationRepository}
import domain.value_objects.{Fail, EventResources, Location, ResourceStock}
import javax.inject.Inject

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

// todo: move this class into a new package (think out the aggregate structure)
class Organizer @Inject() (eventRepository: EventRepository, locationRepository: LocationRepository) {

  def launch(event: Event): Future[Int] = {
    // todo: handle every property requirement (empty resource list & properties characters length -in the model-)
    eventRepository.add(event)
  }

  def subscribe(location: Location): Future[Int] = {
    // todo: handle a non existent event id (get event by id, -in the repo-)
    eventRepository.addLocation(location)
  }

  def lookUpEveryLocation(): Future[Seq[String]] = {
    // todo: use the event repository, and not the location repository
    locationRepository.getAll
  }

  def lookUpResourcesBy(eventId: Int): Future[EventResources] = {
    eventRepository.getBy(eventId).map(event => EventResources(event.favoriteResource, event.resources))
  }

  def set(resourceStock: ResourceStock): Future[Either[Fail, Any]] = {

    def performResourceUpdateTo(storedResource: Resource): Either[Fail, Future[Int]] = {
      checkIllegalResourceAmountIn(storedResource).flatMap(_ => Right(eventRepository.set(resourceStock)))
    }

    def checkIllegalResourceAmountIn(resource: Resource): Either[Fail, Any] = {

      val storedResourceStockAmount = resource.stock
      if (storedResourceStockAmount.isDefined) {

        val newResourceStockIsIllegal = resourceStock.amount > storedResourceStockAmount.get
        if (newResourceStockIsIllegal) {

          Left(Fail(s"This resource maximum stock is $storedResourceStockAmount, " +
                     s"while ${storedResourceStockAmount.get} surpasses that amount."))
        } else Right()

      } else Right()
    }

    def check(changedRowsInDB: Future[Int]): Future[Either[Fail, Any]] = {

      changedRowsInDB.map(amount => {

        if (amount  > 0)
          Right()
        else
          Left(Fail(s"Resource with id ${resourceStock.id} was not found, and hence not updated"))
      })
    }

    for {
      storedResource   <- eventRepository.getResourceBy(resourceStock.id)
      changedRowsInDB  <- performResourceUpdateTo(storedResource)
      operationResult  <- check(changedRowsInDB)
    } yield operationResult
  }
}
