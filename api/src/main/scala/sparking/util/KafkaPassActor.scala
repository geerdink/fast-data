/**
  * Ger van Rossum (2016).
  */
package sparking.util

import akka.actor.{Actor, ActorLogging, Props}
import kafka.consumer.{ConsumerIterator, ConsumerTimeoutException}

object KafkaPassActor {

  def props(source: String, target: String, engine: String => String) =
    Props(classOf[KafkaPassActor], source, target, engine)

  private object Continue

}

class KafkaPassActor(source: String, target: String, engine: String => String) extends Actor with ActorLogging {

  import KafkaPassActor._

  private lazy val connection = KafkaConnection.consumer()

  private lazy val it: ConsumerIterator[Array[Byte], Array[Byte]] = {
    val topicMap = Map(source -> 1)
    val kafkaStream = connection.createMessageStreams(topicMap)(source)(0)
    kafkaStream.iterator()
  }

  private lazy val producer = KafkaProducer(target)

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
      val input = new String(msg.message(), "UTF8")
      val output = engine(input)
      println(s">>> input=${input} output=${output}")
      producer.send(output)
      connection.commitOffsets
      log.debug(s"kafka-consumer-actor message = ${input}")
      self ! Continue

    case Continue =>
      log.debug("kafka-consumer-actor continue without message")
      self ! Continue

  }

}
