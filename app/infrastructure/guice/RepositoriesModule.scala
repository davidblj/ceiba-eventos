package infrastructure.guice

import com.google.inject.AbstractModule
import domain.repositories.EventRepository
import infrastructure.slick.repositories.SlickEventRepository

class RepositoriesModule extends AbstractModule {

  override def configure(): Unit = {

   bind(classOf[EventRepository]).to(classOf[SlickEventRepository])
  }
}
