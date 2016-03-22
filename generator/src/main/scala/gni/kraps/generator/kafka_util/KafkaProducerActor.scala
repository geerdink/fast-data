/**
  * Ger van Rossum (2016).
  */
package gni.kraps.generator.kafka_util

import akka.actor.{Actor, Props}
import gni.kraps.generator.MessageProducer

object KafkaProducerActor {

  def props(id: String, topic: String, brokers: String) = Props(classOf[KafkaProducerActor], id, topic, brokers)

}

class KafkaProducerActor(id: String, topic: String, brokers: String) extends Actor {

  val producer = new MessageProducer(id, topic, brokers)

  def receive = {
    case msg: String => producer.send(msg)
  }

}
