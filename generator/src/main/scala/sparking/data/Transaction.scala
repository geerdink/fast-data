/**
  * Ger van Rossum (2016).
  */
package sparking.data

case class Transaction(user: String, amount: Double, balance: Double) extends CustomerEvent
