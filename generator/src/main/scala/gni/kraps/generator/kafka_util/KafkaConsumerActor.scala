/**
  * Ger van Rossum (2016).
  */
package gni.kraps.generator.kafka_util

import akka.actor.{Actor, ActorLogging, Props}
import kafka.consumer.{ConsumerIterator, ConsumerTimeoutException, KafkaStream}
import kafka.serializer.StringDecoder

object KafkaConsumerActor {

  def props(topic: String, consumer: KafkaKeyValue => Unit) =
    Props(classOf[KafkaConsumerActor], topic, consumer)

  private val decoder = new StringDecoder()

  private object Continue

}

class KafkaConsumerActor(topic: String, consumer: KafkaKeyValue => Unit) extends Actor with ActorLogging {

  import KafkaConsumerActor._

  private lazy val connection = KafkaConnection.consumer()

  private lazy val it: ConsumerIterator[String, String] = {
    val topicMap = Map(topic -> 1)
    val kafkaStream: KafkaStream[String, String] = connection.createMessageStreams(topicMap, decoder, decoder)(topic)(0)
    kafkaStream.iterator()
  }

  private def hasNextInTime = try {
    it.hasNext()
  } catch {
    case cte: ConsumerTimeoutException => false
  }

  self ! Continue

  def receive = {
    case Continue if hasNextInTime =>
      log.debug("kafka-consumer-actor continue")
      val msg = it.next()
      consumer(msg)
      connection.commitOffsets
      log.debug(s"kafka-consumer-actor message = ${msg}")
      self ! Continue

    case Continue =>
      log.debug("kafka-consumer-actor continue without message")
      self ! Continue

  }

}
