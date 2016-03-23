/**
  * Ger van Rossum (2016).
  */
package sparking.rules

import sparking.data.Transaction
import sparking.results.Category

object RuleEngine {

  def defineCategory(transaction: Transaction): Category = {
    val balance = transaction.balance - transaction.amount
    val score = transaction.balance.abs - balance.abs
    if (balance <= 0.0) Category(transaction.user, "Loan", - score)
    else Category(transaction.user, "Saving", score)
  }

  def process(message: String): String = {
    println("Received message from Kafka: " + message)
    Rules.passThrough(message)
  }

}
