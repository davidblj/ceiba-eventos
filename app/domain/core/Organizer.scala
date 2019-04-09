package domain.core

import domain.entities._
import domain.repositories.{EmployeeRepository, EventRepository, LocationRepository}
import domain.value_objects.{EventResources, Fail, Location, ResourceSharedAmount}
import javax.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

// todo: move this class into a new package (think out the aggregate structure)
class Organizer @Inject() (eventRepository: EventRepository, locationRepository: LocationRepository,
                           employeeRepository: EmployeeRepository) {

  def launch(event: Event): Future[Int] = {
    // todo: handle every property requirement (empty resource list & properties characters length -in the model-)
    eventRepository.add(event)
  }

  def subscribe(location: Location): Future[Int] = {
    // todo: handle a non existent event id (get event by id, -in the repo-)
    eventRepository.add(location)
  }

  def lookUpRegisteredLocations(): Future[Seq[String]] = {
    // todo: use the event repository, and not the location repository
    locationRepository.getAll
  }

  def lookUpResourcesBy(eventId: Int): Future[EventResources] = {
    eventRepository.getBy(eventId)
                   .map(event => EventResources(event.favoriteResource, event.resources))
  }

  def set(resourceSharedAmount: ResourceSharedAmount): Future[None.type] = {

    def checkIllegalSharedAmountFor(storedResource: Resource): Future[None.type] = {

      val validationResult =
        if (storedResource.stock.isDefined) {
          compareIncomingResourceWithStoredResourceStock(storedResource)
        } else {
          Right(Unit)
        }

      validationResult match {
        case Right(_) => Future.successful(None)
        case Left(fail) => Future.failed(new Exception(fail.message))
      }
    }

    def compareIncomingResourceWithStoredResourceStock(storedResource: Resource): Either[Fail, Unit.type] = {

      val availableStock = storedResource.stock.get - storedResource.quantity.getOrElse(0)
      val newSharedResourceAmountIsIllegal = resourceSharedAmount.amount > availableStock

      if (newSharedResourceAmountIsIllegal) {
        Left(Fail(s"This resource available stock is $availableStock, " +
                  s"and ${resourceSharedAmount.amount} surpasses that amount."))
      } else {
        Right(Unit)
      }
    }

    def setNewResourceQuantityTo(storedResource: Resource): Future[Int] = {

      val mergedQuantity = storedResource.quantity.getOrElse(0) + resourceSharedAmount.amount
      val mergedResourceQuantityAmount = ResourceSharedAmount(mergedQuantity, resourceSharedAmount.id)
      eventRepository.set(mergedResourceQuantityAmount)
    }

    def check(changedRowsInDB: Int): Future[None.type] = {

      if (changedRowsInDB > 0)
        Future.successful(None)
      else
        Future.failed(new Exception("Resource with id ${resourceStock.id} was not found, and hence not updated"))
    }

    for {
      storedResource   <- eventRepository.getResourceBy(resourceSharedAmount.id)
      _                <- checkIllegalSharedAmountFor(storedResource)
      changedRowsInDB  <- setNewResourceQuantityTo(storedResource)
      operationResult  <- check(changedRowsInDB)
    } yield operationResult
  }

  def searchEmployeesBy(employeeName: String): Future[List[Employee]] = {
    this.employeeRepository.getBy(employeeName)
  }

  def summarizeEventBy(eventId: Int): Future[EventSummary] = {
    eventRepository.getSummaryBy(eventId)
  }

  def signUp(attendant: Attendant): Future[Int] = {
    eventRepository.add(attendant)
  }
}
