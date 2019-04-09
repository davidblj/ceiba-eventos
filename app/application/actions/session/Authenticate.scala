package application.actions.session

import com.google.inject.Inject
import domain.core.Session
import domain.value_objects.{Credentials, Fail, User}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Authenticate @Inject() (session: Session){

  def execute(credentials: Credentials): Future[Either[Fail, User]] = {

    val result = session.logUserWith(credentials)
    result.map {
      case Some(user) => Right(user)
      case None => Left(Fail("Invalid Username or Password"))
    }
  }
}
