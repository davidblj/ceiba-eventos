package domain.value_objects

import domain.models.Resource

case class EventResources(favoriteResource: Option[String], resources: List[Resource]) {}
