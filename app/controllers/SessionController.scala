package controllers

import application.actions.session.Authenticate
import domain.value_objects.Credentials
import infrastructure.play.json.Validator
import javax.inject.Inject
import play.api.mvc._
import infrastructure.play.json.reads.CredentialsReads._
import scala.concurrent.ExecutionContext

class SessionController @Inject() (cc: ControllerComponents, Authenticate: Authenticate, validator: Validator)
                                  (implicit ec: ExecutionContext)
                                  extends AbstractController(cc) {

  def authenticate() = Action.async(validator.validateJson[Credentials]) {
    request => {

      val credentials = request.body
      Authenticate.execute(credentials).map(_ => NoContent)
    }
  }
}
