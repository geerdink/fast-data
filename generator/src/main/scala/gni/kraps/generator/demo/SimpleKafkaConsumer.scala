/**
  * Ger van Rossum (2016).
  */
package gni.kraps.generator.demo

import akka.actor.ActorSystem
import gni.kraps.generator.kafka_util.{KafkaConsumerActor, KafkaKeyValue}

object SimpleKafkaConsumer extends App {

  val t0 = System.nanoTime()

  implicit val system = ActorSystem("alerting-demo")

  val topic = "test"

  println(s"topic = ${topic}")

  def consumer(msg: KafkaKeyValue): Unit = {
    println(s">>> key=${msg.key()}, message=${msg.message()}")
  }

  private val alert = system.actorOf(KafkaConsumerActor.props("test", consumer))


}

