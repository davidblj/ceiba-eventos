package application.actions.session

import domain.services.Session
import domain.value_objects.{Credentials, Fail, User}
import javax.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Authenticate @Inject() (session: Session){

  def execute(credentials: Credentials): Future[Either[Fail, User]] = {

    val result = session.logUserWith(credentials)
    result.map {
      case Some(user) => Right(user)
      case None => Left(Fail("Invalid Username or Password"))
    }
  }
}
