package domain.value_objects

import domain.entities.Resource

case class EventResources(favoriteResource: Option[String], resources: List[Resource]) {}
