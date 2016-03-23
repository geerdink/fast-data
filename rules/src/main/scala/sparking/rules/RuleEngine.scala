/**
  * Ger van Rossum (2016).
  */
package sparking.rules

object RuleEngine {

  def receive(message: String): Unit = {
    println("Received message from Kafka: " + message)
    val appliedRule = Rules.passThrough(message)
    send(appliedRule)
  }

  def send(message: String){
    //Predictor.HelloWorld(message)
    //Put it on Kafka
  }

}
