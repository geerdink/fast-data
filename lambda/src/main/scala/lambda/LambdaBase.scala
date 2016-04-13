package lambda

import akka.actor.ActorSystem

trait LambdaBase {
  implicit val system = ActorSystem("fast-data")

  def main(args: Array[String]) {

  }
}
