/**
  * Ger van Rossum (2016).
  */
package sparking.generator

import java.util.concurrent.TimeUnit
import java.util.{Random}

import akka.actor.ActorSystem
import sparking.data._
import sparking.util.KafkaProducer

import scala.concurrent.duration.Duration

object Generator extends App {
  if (args.length != 2) throw new Exception("Please specify the intervals of the messages (search_history, social_media) in milliseconds, e.g. 'generator 1000 2000'")

  val actorSystem = ActorSystem()

  val scheduler = actorSystem.scheduler

  val producer1 = KafkaProducer("search_history")
  val producer2 = KafkaProducer("social_media")

  val randomUserName = new RandomSelection("Alice", "Bob", "Zelda", "Betty", "Frank", "Rose", "Louise", "Sebastian", "Robert", "Bumba", "Ernie", "Eric", "Zoe", "Emma", "Bea", "Joe", "Miranda")
  val randomProductCategory = new RandomSelection("Phone", "Sneakers", "Game", "Book")
  def randomProductName(category: String) = category match  {
    case "Phone" => new RandomSelection("iPhone 6s", "HTC One", "Motorola Moto G", "One Plus Two", "Nokia Lumia", "Sony Experia Z")
    case "Sneakers" => new RandomSelection("Nike Air Max", "Adidas Originals", "Asics Gel", "Converse All Star", "Vans Atwood", "New Balance")
    case "Book" => new RandomSelection("The Art of Prolog", "Godel, Escher, Bach", "Structured Design", "Patterns of EAA", "The Elegant Universe", "The Hidden Reality", "How to win at Texas Hold'em", "On The Shoulders Of Giants")
    case "Game" => new RandomSelection("Mass Effect 3", "Call of Duty: Black Ops", "Tom Clancy's The Division", "Deus Ex: Mankind Divided", " Far Cry Primal", "Hitman 6", "Doom 2016", "Dark Souls III", "Street Fighter V")
  }
  val randomScore = new RandomInt()

  val task1 = new Runnable {
    def run() {
      val cat = randomProductCategory.next()
      val productScore = ProductScore(randomUserName.next(), cat, randomProductName(cat).next(), randomScore.next())
      producer1.send(productScore.text)
    }
  }

  val task2 = new Runnable {
    def run() {
      val socialMediaEvent = SocialMediaEvent(randomUserName.next(), new RandomString(new RandomInt().next() + 50).next())
      producer2.send(socialMediaEvent.text)
    }
  }

  implicit val executor = actorSystem.dispatcher

  scheduler.schedule(
    initialDelay = Duration(2, TimeUnit.SECONDS),
    interval = Duration(args(0).toInt, TimeUnit.MILLISECONDS),
    runnable = task1)

  scheduler.schedule(
    initialDelay = Duration(2, TimeUnit.SECONDS),
    interval = Duration(args(1).toInt, TimeUnit.MILLISECONDS),
    runnable = task2)
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

class RandomString(length: Int) {
  def next(): String = scala.util.Random.alphanumeric.take(length).mkString
}
