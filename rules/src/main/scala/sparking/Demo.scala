/**
  * Ger van Rossum (2016).
  */
package sparking

import akka.actor.ActorSystem
import sparking.rules.Rules
import sparking.util.KafkaPassActor

object Demo extends App {

  implicit val system = ActorSystem("sparking-demo")

  val source = "test"

  val target = "sparking"

  println(s">>> source topic = ${source}, target topic = ${target}")

  private val alert = system.actorOf(KafkaPassActor.props(source, target, Rules.passThrough))


}
