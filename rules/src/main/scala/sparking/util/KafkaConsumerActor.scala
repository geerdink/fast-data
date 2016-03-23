/**
  * Ger van Rossum (2016).
  */
package sparking.util

import akka.actor.{Actor, ActorLogging, Props}
import kafka.consumer.{ConsumerIterator, ConsumerTimeoutException}

object KafkaConsumerActor {

  def props(topic: String, consumer: String => Unit) =
    Props(classOf[KafkaConsumerActor], topic, consumer)

  private object Continue

}

class KafkaConsumerActor(topic: String, consumer: String => Unit) extends Actor with ActorLogging {

  import KafkaConsumerActor._

  private lazy val connection = KafkaConnection.consumer()

  private lazy val it: ConsumerIterator[Array[Byte], Array[Byte]] = {
    val topicMap = Map(topic -> 1)
    val kafkaStream = connection.createMessageStreams(topicMap)(topic)(0)
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
      val text = new String(msg.message(), "UTF8")
      consumer(text)
      connection.commitOffsets
      log.debug(s"kafka-consumer-actor message = ${text}")
      self ! Continue

    case Continue =>
      log.debug("kafka-consumer-actor continue without message")
      self ! Continue

  }

}
