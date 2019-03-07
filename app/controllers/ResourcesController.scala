package controllers

import application.actions.events.{ChangeResourceAmount, GetAllResources}
import domain.value_objects.{Fail, ResourceQuantityAmount}
import infrastructure.play.json.Validator
import infrastructure.play.json.writes.ResourcesWrites.eventResourcesWrites
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import infrastructure.play.json.writes.FailWrites.failWrites

import scala.concurrent.ExecutionContext

class ResourcesController @Inject()(cc: ControllerComponents, GetAllResources: GetAllResources,
                                    ChangeResourceAmount: ChangeResourceAmount, validator: Validator)
                                   (implicit ec: ExecutionContext)
                                   extends AbstractController(cc){

  def getAllResources(eventId: Int): Action[AnyContent] = Action.async {
    _ => {

      // todo: handle exception
      GetAllResources.execute(eventId).map(result => {
        Ok(Json.toJson(result))
      })
    }
  }

  def changeResourceAmount(eventId: Int, resourceId: Int, deliveredAmount: Int): Action[AnyContent] = Action.async {
    _ => {

      val resourceStock = ResourceQuantityAmount(deliveredAmount, resourceId)
      ChangeResourceAmount.execute(resourceStock).map(_ => {
        NoContent
      }).recover({
        case e: Exception => BadRequest(Json.toJson(Fail(e.getMessage)))
      })
    }
  }
}
