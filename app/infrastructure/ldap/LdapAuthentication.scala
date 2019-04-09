package infrastructure.ldap

import domain.services.Authentication
import domain.value_objects.{Credentials, User}

import scala.concurrent.Future

class LdapAuthentication extends Authentication {

  override def authenticate(credentials: Credentials): Future[Option[User]] = {
    Future.successful(Some(User("Laura Bollago", "laurBoll")))
  }
}
