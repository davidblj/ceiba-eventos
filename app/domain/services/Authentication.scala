package domain.services

import domain.value_objects.{Credentials, User}

import scala.concurrent.Future

trait Authentication {
  def authenticate(credentials: Credentials): Future[Option[User]]
}
