package infrastructure.play.json.reads

import domain.value_objects.Credentials
import play.api.libs.json._

object CredentialsReads {
  implicit val credentialReads: Reads[Credentials] = Json.reads[Credentials]
}
