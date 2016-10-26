package etl.domain

case class Customer (id: Int, name: String, age: Int, title: String, street: String, number: Int, postCode: String, email: String, premium: Boolean)

object CustomerHelper {
  def getCustomer(raw: String): Customer = {
    val parts = raw.split(',')
    Customer(parts(0).toInt, parts(1), parts(2).toInt, parts(3), parts(4), parts(5).toInt, parts(6), parts(7), parts(8).toBoolean)
  }
}
