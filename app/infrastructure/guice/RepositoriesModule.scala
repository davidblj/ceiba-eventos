package infrastructure.guice

import com.google.inject.AbstractModule
import domain.repositories.{EmployeeRepository, EventRepository, LocationRepository}
import infrastructure.slick.repositories.{SlickEmployeeRepository, SlickEventRepository, SlickLocationRepository, SlickResourceRepository}

class RepositoriesModule extends AbstractModule {

  override def configure(): Unit = {

   bind(classOf[EventRepository]).to(classOf[SlickEventRepository])
   bind(classOf[LocationRepository]).to(classOf[SlickLocationRepository])
   bind(classOf[EmployeeRepository]).to(classOf[SlickEmployeeRepository])
   bind(classOf[SlickResourceRepository])
  }
}
