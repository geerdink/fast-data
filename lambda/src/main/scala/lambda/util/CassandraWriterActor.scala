package lambda.util

import akka.actor.{Actor, ActorLogging, Props}
import kafka.consumer.{ConsumerIterator, ConsumerTimeoutException}

object CassandraWriterActor {
  def props(topic: String) =
    Props(classOf[CassandraWriterActor], topic)

  private object Continue
}

class CassandraWriterActor(topic: String) extends Actor with ActorLogging {

  import CassandraWriterActor._

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
      try {
        val msg = it.next()
        val input = new String(msg.message(), "UTF8")

        println("createOfferingUpdate, input = " + input)

        CassandraHelper.updateScore(CassandraHelper.createProductScore(input))
        connection.commitOffsets

        self ! Continue
      }
      catch {
        case e: Exception => {
          log.error("cassandra-writer-actor received an invalid message! Error = " + e.getMessage)
          self ! Continue
          //throw e
        }
      }

    case Continue => {
      log.debug("cassandra-writer-actor continue without message")
      self ! Continue
    }
  }
}
