package controllers

import application.actions.events.GetAllResources
import infrastructure.play.json.writes.Resources.eventResourcesWrites
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.ExecutionContext

class ResourcesController @Inject()(cc: ControllerComponents, GetAllResources: GetAllResources)
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

  /*def changeResourceAmount() = Action.async {

    _ => {

    }
  }*/
}
