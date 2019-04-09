package domain.core

import com.google.inject.Inject
import domain.services.Authentication
import domain.value_objects.{Credentials, User}

import scala.concurrent.Future

class Session @Inject() (authentication: Authentication){

  def logUserWith(credentials: Credentials): Future[Option[User]] = {
    authentication.authenticate(credentials)
  }
}
