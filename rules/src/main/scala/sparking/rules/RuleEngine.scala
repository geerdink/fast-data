/**
  * Ger van Rossum (2016).
  */
package sparking.rules

import sparking.data.Transaction
import sparking.results.Category

object RuleEngine {

  def processTransaction(transaction: String):String ={
    Rules.convertDefineCategory(transaction)
  }

  def process(message: String): String = {
    println("Received message from Kafka: " + message)
    Rules.passThrough(message)
  }

}
