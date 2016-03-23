/**
  * Ger van Rossum (2016).
  */
package sparking.util

import akka.actor.{Actor, Props}

object KafkaProducerActor {

  def props(topic: String) = Props(classOf[KafkaProducerActor], topic)

}

class KafkaProducerActor(topic: String) extends Actor {

  val producer = MessageProducer(topic)

  def receive = {
    case msg: String => producer.send(msg)
  }

}
