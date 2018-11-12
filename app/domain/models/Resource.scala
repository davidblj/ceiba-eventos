package domain.models

case class Resource (name: String,
                     price: Float,
                     description: Option[String] = None,
                     stock: Option[Int] = None,
                     eventId: Option[Int] = None) {

  def setId(id: Int): Resource = {
    Resource(name, price, description, stock, Some(id))
  }
}