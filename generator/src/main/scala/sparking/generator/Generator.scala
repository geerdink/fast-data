/**
  * Ger van Rossum (2016).
  */
package sparking.generator

import java.util.Date
import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import sparking.util.KafkaProducer

import scala.concurrent.duration.Duration

object Generator extends App {

  val actorSystem = ActorSystem()

  val scheduler = actorSystem.scheduler

  val producer = KafkaProducer("test")

  val task = new Runnable {
    def run() {
      producer.send(s"${new Date()} : generated message")
    }
  }

  implicit val executor = actorSystem.dispatcher

  scheduler.schedule(
    initialDelay = Duration(10, TimeUnit.SECONDS),
    interval = Duration(60, TimeUnit.SECONDS),
    runnable = task)


}
