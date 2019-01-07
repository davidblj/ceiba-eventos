package infrastructure.guice

import com.google.inject.AbstractModule
import domain.repositories.{EventRepository, LocationRepository}
import infrastructure.slick.repositories.{SlickEventRepository, SlickLocationRepository}

class RepositoriesModule extends AbstractModule {

  override def configure(): Unit = {

   bind(classOf[EventRepository]).to(classOf[SlickEventRepository])
   bind(classOf[LocationRepository]).to(classOf[SlickLocationRepository])
  }
}
