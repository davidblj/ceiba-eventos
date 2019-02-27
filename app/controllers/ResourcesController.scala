package controllers

import application.actions.events.{ChangeResourceAmount, GetAllResources}
import domain.value_objects.ResourceStock
import infrastructure.play.json.Validator
import infrastructure.play.json.writes.Resources.eventResourcesWrites
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

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

  def changeResourceAmount(resourceId: Int, stock: Int) = Action.async {
    _ => {

      val resourceStock = ResourceStock(stock, resourceId)
      ChangeResourceAmount.execute(resourceStock).map {
        case Left(_) => BadRequest
        case Right(_) => NoContent
      }
    }
  }
}
