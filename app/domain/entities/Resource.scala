package domain.entities

case class Resource (name: String,
                     price: Float,
                     description: Option[String] = None,
                     stock: Option[Int] = None,
                     id: Option[Int] = None,
                     quantity: Option[Int] = Some(1)) {
}
