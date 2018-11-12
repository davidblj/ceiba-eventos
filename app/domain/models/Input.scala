package domain.models

case class Input(name: String,
                 price: Float,
                 description: Option[String] = None,
                 eventId: Option[Int] = None) {

  def setId(id: Int): Input = {
    Input(name, price, description, Some(id))
  }
}
