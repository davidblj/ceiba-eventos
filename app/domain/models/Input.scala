package domain.models

case class Input(name: String,
                 price: Float,
                 description: Option[String] = None) {
}
