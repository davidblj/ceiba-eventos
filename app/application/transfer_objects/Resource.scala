package application.transfer_objects

case class Resource(name: String,
                    price: Float,
                    description: Option[String],
                    stock: Option[Int])
