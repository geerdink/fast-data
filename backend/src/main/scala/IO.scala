object IO{

  def receive(message: String): Unit = {
    println("Received message from Kafka: " + message)
    Rules.passThrough(message)

  }

  def callPrediction(message: String){

  }
}