/**
  * Ger van Rossum (2016).
  */
package sparking.generator

import java.util.concurrent.TimeUnit
import java.util.{Date, Random}

import akka.actor.ActorSystem
import sparking.data.Transaction
import sparking.util.KafkaProducer

import scala.concurrent.duration.Duration

object Generator extends App {

  val actorSystem = ActorSystem()

  val scheduler = actorSystem.scheduler

  val producer = KafkaProducer("test")

  val randomUser = new RandomSelection("Alice", "Bob", "Zelda")

  val randomAmount = new RandomDouble(-30.0, 80.0)

  val randomBalance = new RandomDouble(100.0, 500.0)

  val task = new Runnable {
    def run() {
      val transaction = Transaction(randomUser.next(), randomAmount.next(), randomBalance.next())
      producer.send(transaction.text)
    }
  }


  implicit val executor = actorSystem.dispatcher

  scheduler.schedule(
    initialDelay = Duration(10, TimeUnit.SECONDS),
    interval = Duration(60, TimeUnit.SECONDS),
    runnable = task)


}

class RandomSelection(element: String*) {

  val array = element.toArray

  val rand = new Random(System.currentTimeMillis())

  def next(): String = array(rand.nextInt(array.size))

}

class RandomDouble(avg: Double, std: Double) {

  val rand = new Random(System.currentTimeMillis())

  def next(): Double = rand.nextGaussian() * std + avg

}