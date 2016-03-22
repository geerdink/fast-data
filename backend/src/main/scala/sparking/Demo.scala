package sparking

import akka.actor.ActorSystem
import sparking.k_consumer.KafkaConsumerActor
import sparking.rules.RuleEngine

object Demo extends App {

  implicit val system = ActorSystem("sparking-demo")

  val topic = "test"

  private val alert = system.actorOf(KafkaConsumerActor.props("test", RuleEngine.receive))

}
