/**
  * Ger van Rossum (2016).
  */
package sparking.generator

import java.util.concurrent.TimeUnit
import java.util.{Date, Random}

import akka.actor.ActorSystem
import sparking.data.ProductScore
import sparking.util.KafkaProducer

import scala.concurrent.duration.Duration

object Generator extends App {

  val actorSystem = ActorSystem()

  val scheduler = actorSystem.scheduler

  val producer1 = KafkaProducer("search_history")

  val randomUserName = new RandomSelection("Alice", "Bob", "Zelda", "Betty", "Frank", "Rose", "Louise", "Sebastian", "Robert", "Bumba", "Ernie", "Eric", "Zoe", "Emma", "Bea", "Joe", "Miranda")
  val randomProductCategory = new RandomSelection("Phone", "Sneakers", "Shirt", "Game", "Book")
  def randomProductName(category: String) = category match  {
    case "Phone" => new RandomSelection("iPhone 6s", "HTC One", "Motorola Moto G", "One Plus Two", "Nokia Lumia", "Sony Experia Z")
    case "Sneakers" => new RandomSelection("Nike Air Max", "Clarks Desert Boots", "Adidas Classics")
    case "Shirt" => new RandomSelection("")
    case "Book" => new RandomSelection("The Art of Prolog", "Godel, Escher, Bach", "Structured Design", "Patterns of Enterprise Application Architecture", "The Elegant Universe", "The Hidden Reality", "How to win at Texas Hold'em Poker", "On The Shoulders Of Giants")
    case "Game" => new RandomSelection("Mass Effect 3", "Black Ops", "")
  }

  val randomScore = new RandomInt()

  val task = new Runnable {
    def run() {
      val cat = randomProductCategory.next()
      val productScore = ProductScore(randomUserName.next(), cat, randomProductName(cat).next(), randomScore.next())
      producer1.send(productScore.text)
    }
  }

  implicit val executor = actorSystem.dispatcher

  scheduler.schedule(
    initialDelay = Duration(2, TimeUnit.SECONDS),
    interval = Duration(100, TimeUnit.MILLISECONDS),
    runnable = task)
}

class RandomSelection(element: String*) {
  val array = element.toArray
  val rand = new Random(System.currentTimeMillis())
  def next(): String = array(rand.nextInt(array.size))
}

class RandomInt() {
  val rand = new Random(System.currentTimeMillis())
  def next(): Int = rand.nextInt(10)
}

class RandomDouble(avg: Double, std: Double) {
  val rand = new Random(System.currentTimeMillis())
  def next(): Double = rand.nextGaussian() * std + avg
}