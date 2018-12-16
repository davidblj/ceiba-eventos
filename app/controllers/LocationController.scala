package controllers

import javax.inject._
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class LocationController @Inject() (cc: ControllerComponents)
                                   (implicit  ec: ExecutionContext)
                                    extends AbstractController(cc) {

  def addEventLocation() = Action {
    // todo: call the command
    Ok
  }
}
