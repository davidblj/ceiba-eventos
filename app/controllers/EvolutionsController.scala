package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

class EvolutionsController @Inject()(cc: ControllerComponents)
                                    extends AbstractController(cc) {

  def executeEvolutions() = Action {
    Ok("get handler to execute db evolutions")
  }
}