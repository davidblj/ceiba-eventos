package domain.core

import domain.services.Authentication
import domain.value_objects.{Credentials, User}
import javax.inject.Inject

import scala.concurrent.Future

class Session @Inject() (authentication: Authentication){

  def logUserWith(credentials: Credentials): Future[Option[User]] = {
    authentication.authenticate(credentials)
  }
}
