package lambda.util

import akka.actor.{Actor, ActorLogging, Props}
import lambda.data._

object CassandraWriterActor {
  def props = Props(classOf[CassandraWriterActor])

  private object Continue
}

class CassandraWriterActor extends Actor with ActorLogging {
  import CassandraWriterActor._

  def receive = {
    case ps : ProductScore =>
      try {
        CassandraHelper.insertScore(ps)
      }
      catch {
        case e: Exception => {
          log.error("cassandra-writer-actor received an invalid message while inserting product score! Error = " + e.getMessage)
          self ! Continue
        }
      }
    case sme : SocialMediaEvent =>
      try {
        CassandraHelper.insertSocialMediaEvent(sme)
      }
      catch {
        case e: Exception => {
          log.error("cassandra-writer-actor received an invalid message while inserting social media event! Error = " + e.getMessage)
          self ! Continue
        }
      }
    case uuc : User =>
      try {
        CassandraHelper.updateUserCategory(uuc)
      }
      catch {
        case e: Exception => {
          log.error("cassandra-writer-actor received an invalid message while updating user category! Error = " + e.getMessage)
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
