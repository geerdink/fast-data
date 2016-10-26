package etl.domain

case class Order (id: Int, customer: Customer, product: Product, amount: Int)

case class Product (name: String, url: String, price: Double)

object OrderHelper {
  def getOrder(raw: String) = {
    val parts = raw.split(',')
    // TODO: get customer and orders properly
    val customer = Customer(parts(1).toInt, "test", 21, "mr.", "Kerkplein", 4, "1234", "test@email.nl", true)
    val product = Product("something", "http://test.html", 15.34)
    Order(parts(0).toInt, customer, product, parts(2).toInt)
  }
}