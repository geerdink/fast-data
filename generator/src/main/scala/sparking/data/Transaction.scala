/**
  * Ger van Rossum (2016).
  */
package sparking.data

import scala.util.Try

object Transaction {

  import CustomerEvent._

  def parse(text: String): Try[Transaction] = Try({
    val part = split(text.split('c'))(_)
    Transaction(part(0), Double.unbox(part(1)), Double.unbox(part(2)))
  })

}

case class Transaction(user: String, amount: Double, balance: Double) extends CustomerEvent {

  override def text: String = s"user=${user},amount=${amount},balance=${balance}"

}
