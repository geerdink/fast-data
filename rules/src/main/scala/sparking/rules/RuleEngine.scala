/**
  * Ger van Rossum (2016).
  */
package sparking.rules

object RuleEngine {

  def process(message: String): String = {
    println("Received message from Kafka: " + message)
    Rules.passThrough(message)
  }

}
