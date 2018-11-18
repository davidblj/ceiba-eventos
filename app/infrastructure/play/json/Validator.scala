package infrastructure.play.json

import javax.inject.Inject
import play.api.libs.json._
import play.api.mvc.Results._
import play.api.mvc.{BodyParser, PlayBodyParsers}
import scala.concurrent.ExecutionContext

class Validator @Inject()(parser: PlayBodyParsers)
                         (implicit exec: ExecutionContext) {

  def validateJson[A : Reads]: BodyParser[A] = parser.json.validate(
    _.validate[A].asEither.left.map(e => UnprocessableEntity(JsError.toJson(e)))
  )
}