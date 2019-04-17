package controllers

import application.actions.session.Authenticate
import domain.value_objects.{Credentials, User}
import infrastructure.play.json.Validator
import infrastructure.play.json.reads.CredentialsReads._
import infrastructure.play.json.writes.FailWrites._
import infrastructure.play.json.writes.UserWrites._
import javax.inject.Inject
import pdi.jwt.{JwtAlgorithm, JwtJson}
import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

class SessionController @Inject() (cc: ControllerComponents, Authenticate: Authenticate, validator: Validator,
                                   playConfiguration: Configuration)
                                  (implicit ec: ExecutionContext)
                                   extends AbstractController(cc) {

  def authenticate(): Action[Credentials] = Action.async(validator.validateJson[Credentials]) {
    request => {

      val credentials = request.body
      Authenticate.execute(credentials).map {

        case Right(user) =>
          val authenticationHeader = generateToken(user)
          Ok(Json.toJson(user)).withHeaders(("Authentication", authenticationHeader))

        case Left(fail) => BadRequest(Json.toJson(fail))
      }
    }
  }

  def generateToken(user: User): String = {

    val key = playConfiguration.underlying.getString("play.jwt.secret")
    val payload = Json.obj(("username", user.username))
    val algorithm = JwtAlgorithm.HS256

    "Bearer " + JwtJson.encode(payload, key, algorithm)
  }
}
