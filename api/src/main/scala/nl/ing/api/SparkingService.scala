package nl.ing.api

import akka.actor.Actor

import com.datastax.driver.core.{Row, Cluster, QueryOptions, ConsistencyLevel}
import sparking.util.{KafkaProducerActor, KafkaProducer}
import spray.http.HttpHeaders.RawHeader
import spray.routing._
import spray.http._
import MediaTypes._

import org.json4s._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{ writePretty }


case class Offer(name: String, score: Double)
case class OfferList(offers: List[Offer])


class APIService extends Actor with SparkingService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(apiRoute)

}

// this trait defines our service behavior independently from the service actor
trait SparkingService extends HttpService {


  implicit val formats = Serialization.formats(
    ShortTypeHints(
      List(
        classOf[Offer]
      )
    )
  )
  def sendToKafka(msg: String): Unit  =  {
    val kafkaActor = actorRefFactory.actorOf(KafkaProducerActor.props("test"))
    kafkaActor ! msg
  }

    def locationToKafka(userId: String,location: Option[String]) = location match {
      case Some(s) => sendToKafka(s"user=$userId,location=$s")
      case None => println("Error problem for locations to kafka")
    }

  def processFeedback(userId: String,  cat: Option[String], score: Option[String], location: Option[String]): String = {
    println(s"Processing feedback $userId  cat: $cat , score $score" )
    val scoreDouble = score.get.toDouble
    val catString = cat.get

    val msg = (s"user=$userId,category=$catString,score=$scoreDouble")
    sendToKafka(msg)

    Thread.sleep(500)

    getOffersFromDb(userId,location)
  }

  def getOffersFromDb(userId: String,  location: Option[String]): String =  {

    locationToKafka(userId,location)
//    val uri = CassandraConnectionUri("cassandra://172.16.33.16:9042")
    val uri = CassandraConnectionUri("cassandra://localhost:9042")
    println("uri set")
    val session = DatabaseHelper.createSessionAndInitKeyspace(uri)
    println("session set")
    val result = session.execute("SELECT * FROM sparking.offers WHERE user_name='"+userId+"' ALLOW FILTERING;")
    println("query done")
    val resultList = result.all()

    val lengthResults = resultList.size()
    val offerList: List[Offer] = {
      var offers: List[Offer] = Nil
      var a = 0
      // for loop execution with a range
      for (a <- 0 until lengthResults) {
        println(resultList.get(a))
        var offer_name = resultList.get(a).getString(1)
        val score = resultList.get(a).getDouble(2)
        offers = Offer(offer_name, score) :: offers
      }
      println(offers)
      offers
    }
    println(offerList)
    writePretty(offerList)

  }
  def getOffers(userId: String): String =  {
    val belegOffer = Offer("Beleggen", 0.42)
    val spaarOffer = Offer("Sparen", 0.52)
    val leenOffer = Offer("Lenen", 0.32)
    val offerList = List(belegOffer, leenOffer, spaarOffer)

    writePretty(offerList)
  }

  val apiRoute =
    path("") {
      get {
        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>Say hello to <i>spray-routing</i> on <i>spray-can</i>!</h1>
              </body>
            </html>
          }
        }
      }
    } ~ //This is the PATH separator for adding more paths
    path("hello" / Segment) { input => //The pathvariable will be /hello/{pathVariable}
      get{
        respondWithHeader(RawHeader("Access-Control-Allow-Origin","*")){
          respondWithMediaType(`text/plain`) {
            complete{
              getOffers(input)
            }
          }
        }
      }
    } ~
  path("getOffers" / Segment) { input => //The pathvariable will be /getOffers/{pathVariable}
    get {
      respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
        respondWithMediaType(`text/plain`) {
          parameters('location.?) { (location) =>
            complete {
              getOffersFromDb(input, location)
            }
          }
        }
        }
      }
    } ~
      path("feedback" / Segment) { input => //The pathvariable will be /feedback/{pathVariable}?cat=test&score=0.42&location=US
        get {
          respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
            respondWithMediaType(`text/plain`) {
              parameters('cat.?, 'score.?, 'location.?) { (cat, score, location) =>
                complete {
                  processFeedback(input, cat, score, location)
                }
              }
            }
          }
        }
      }
}
