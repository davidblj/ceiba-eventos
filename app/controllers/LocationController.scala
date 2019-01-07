package controllers

import application.actions.events.{AddEventLocation, GetAllLocations}
import domain.data_containers.Location
import infrastructure.play.json.reads.LocationReads._
import javax.inject._
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import infrastructure.play.json.Validator
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext

@Singleton
class LocationController @Inject() (cc: ControllerComponents, AddEventLocation: AddEventLocation,
                                    GetAllLocations: GetAllLocations, validator: Validator)
                                   (implicit  ec: ExecutionContext)
                                    extends AbstractController(cc) {

  def addEventLocation(): Action[Location] = Action.async(validator.validateJson[Location]) {
    request => {

      val location = request.body
      AddEventLocation.execute(location).map(_ => {
        NoContent
      })
    }
  }

  def getAllLocations() = Action.async {
    _ => {

      GetAllLocations.execute().map(result => {
        Ok(Json.toJson(result))
      })
    }
  }
}
