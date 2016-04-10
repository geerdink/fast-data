/**
  * Ger van Rossum (2016).
  */
package lambda.util

import akka.actor.{Actor, Props}

object KafkaProducerActor {

  def props(topic: String) = Props(classOf[KafkaProducerActor], topic)

}

class KafkaProducerActor(topic: String) extends Actor {

  val producer = KafkaProducer(topic)

  def receive = {
    case msg: String => producer.send(msg)
  }

}
