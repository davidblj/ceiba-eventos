package infrastructure.ldap

import domain.external_services.Authentication
import domain.value_objects.{Credentials, User}

import scala.concurrent.Future

class LdapAuthentication extends Authentication {

  override def authenticate(credentials: Credentials): Future[Option[User]] = {

    if (credentials.username == "lauraBol" && credentials.password == "laura27") {
      Future.successful(Some(User("Laura Bollago", "laurBoll")))
    } else {
      Future.successful(None)
    }
  }
}
