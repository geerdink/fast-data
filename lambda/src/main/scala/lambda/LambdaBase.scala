package lambda

import akka.actor.ActorSystem
import lambda.util.CassandraWriterActor

trait LambdaBase {
  implicit val system = ActorSystem("fast-data")
  val cassandraWriter = system.actorOf(CassandraWriterActor.props("search_history"))

  def main(args: Array[String]) {
    // initialize Cassandra writer actor
  }
}
