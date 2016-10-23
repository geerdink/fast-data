package lambda.util

import akka.actor.{Actor, ActorLogging, Props}
import lambda.domain._

object CassandraWriterActor {
  def props = Props(classOf[CassandraWriterActor])

  private object Continue
}

class CassandraWriterActor extends Actor with ActorLogging {
  import CassandraWriterActor._

  def receive = {
    case cps : CarParkScore =>
      try {
        CassandraHelper.insertScore(cps)
      }
      catch {
        case e: Exception => {
          log.error("cassandra-writer-actor received an invalid message while inserting car park score! Error = " + e.getMessage)
          self ! Continue
        }
      }
    case sme : CarLocationEvent =>
      try {
        //CassandraHelper.insertSocialMediaEvent(sme)
      }
      catch {
        case e: Exception => {
          log.error("cassandra-writer-actor received an invalid message while inserting car location event! Error = " + e.getMessage)
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
