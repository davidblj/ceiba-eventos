package infrastructure.guice

import com.google.inject.AbstractModule
import domain.repositories.{AttendantRepository, EventRepository, LocationRepository}
import infrastructure.slick.repositories.{SlickAttendantRepository, SlickEventRepository, SlickLocationRepository, SlickResourceRepository}

class RepositoriesModule extends AbstractModule {

  override def configure(): Unit = {

   bind(classOf[EventRepository]).to(classOf[SlickEventRepository])
   bind(classOf[LocationRepository]).to(classOf[SlickLocationRepository])
   bind(classOf[AttendantRepository]).to(classOf[SlickAttendantRepository])
   bind(classOf[SlickResourceRepository])
  }
}
