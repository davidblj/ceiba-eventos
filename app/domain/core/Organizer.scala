package domain.core

import domain.models.{Employee, Event, Resource}
import domain.repositories.{EmployeeRepository, EventRepository, LocationRepository}
import domain.value_objects.{EventResources, Fail, Location, ResourceQuantityAmount}
import javax.inject.Inject

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

// todo: move this class into a new package (think out the aggregate structure)
class Organizer @Inject() (eventRepository: EventRepository, locationRepository: LocationRepository,
                           employeeRepository: EmployeeRepository) {

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
    eventRepository.getBy(eventId)
                   .map(event => EventResources(event.favoriteResource, event.resources))
  }

  // update `resource` set `quantity` = 0 where `resource`.`id` = 16
  def set(resourceQuantityAmount: ResourceQuantityAmount): Future[None.type] = {

    def checkIllegalQuantityAmountFor(storedResource: Resource): Future[None.type] = {

      val validationResult =
        if (storedResource.stock.isDefined) {
          compareIncomingResourceQuantityAmountAndResourceStock(storedResource)
        } else {
          Right(Unit)
        }

      validationResult match {
        case Right(_) => Future.successful(None)
        case Left(fail) => Future.failed(new Exception(fail.message))
      }
    }

    def compareIncomingResourceQuantityAmountAndResourceStock(storedResource: Resource): Either[Fail, Unit.type] = {

      val availableStock = storedResource.stock.get - storedResource.quantity.getOrElse(0)
      val newResourceQuantityAmountIsIllegal = resourceQuantityAmount.amount > availableStock

      if (newResourceQuantityAmountIsIllegal) {
        Left(Fail(s"This resource available stock is $availableStock, " +
                  s"and ${resourceQuantityAmount.amount} already surpasses that amount."))
      } else {
        Right(Unit)
      }
    }

    def setNewResourceQuantityFrom(storedResource: Resource): Future[Int] = {

      val mergedQuantity = storedResource.quantity.getOrElse(0) + resourceQuantityAmount.amount
      val mergedResourceQuantityAmount = ResourceQuantityAmount(mergedQuantity, resourceQuantityAmount.id)
      eventRepository.set(mergedResourceQuantityAmount)
    }

    def check(changedRowsInDB: Int): Future[None.type] = {

      if (changedRowsInDB > 0)
        Future.successful(None)
      else
        Future.failed(new Exception("Resource with id ${resourceStock.id} was not found, and hence not updated"))
    }

    for {
      storedResource   <- eventRepository.getResourceBy(resourceQuantityAmount.id)
      _                <- checkIllegalQuantityAmountFor(storedResource)
      changedRowsInDB  <- setNewResourceQuantityFrom(storedResource)
      operationResult  <- check(changedRowsInDB)
    } yield operationResult
  }

  def getEmployeesBy(employeeName: String): Future[List[Employee]] = {
    this.employeeRepository.getBy(employeeName)
  }
}
