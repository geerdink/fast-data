package lambda.util

import akka.actor.{Actor, ActorLogging, Props}
import lambda.ProductScore

object CassandraWriterActor {
  def props = Props(classOf[CassandraWriterActor])

  private object Continue
}

class CassandraWriterActor extends Actor with ActorLogging {
  import CassandraWriterActor._

  def receive = {
    case ps : ProductScore =>
      try {
        CassandraHelper.updateScore(ps)
      }
      catch {
        case e: Exception => {
          log.error("cassandra-writer-actor received an invalid message! Error = " + e.getMessage)
          self ! Continue
        }
      }
    case Continue => {
      log.info("cassandra-writer-actor continue without message")
      self ! Continue
    }
    case _ => {
      log.warning("cassandra-writer-actor continue unknown message")
      self ! Continue
    }
  }
}
