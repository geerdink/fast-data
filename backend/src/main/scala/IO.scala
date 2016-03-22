object IO{

  def receive(message: String): Unit = {
    println("Received message from Kafka: " + message)
    val appliedRule = Rules.passThrough(message)
    callPrediction(appliedRule)
  }

  def callPrediction(message: String){
    Predictor.HelloWorld(message)
  }
}