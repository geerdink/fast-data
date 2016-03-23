package nl.ing.api

import akka.actor.Actor
import java.net.URI

import com.datastax.driver.core.{Row, Cluster, QueryOptions, ConsistencyLevel}
import spray.http.HttpHeaders.RawHeader
import spray.routing._
import spray.http._
import MediaTypes._

import org.json4s._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{ writePretty }
import scala.collection.mutable.Map

//import org.apache.spark.sql._


case class Offer(id: Long, name: String, score: Double)
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



  //taken from: http://manuel.kiessling.net/2015/01/19/setting-up-a-scala-sbt-multi-project-with-cassandra-connectivity-and-migrations/
  object DatabaseHelper {

    def createSessionAndInitKeyspace(uri: CassandraConnectionUri,
                                     defaultConsistencyLevel: ConsistencyLevel = QueryOptions.DEFAULT_CONSISTENCY_LEVEL) = {
      val cluster = new Cluster.Builder().
        addContactPoints(uri.hosts.toArray: _*).
        withPort(uri.port).
        withQueryOptions(new QueryOptions().setConsistencyLevel(defaultConsistencyLevel)).build

      val session = cluster.connect
      session.execute(s"USE ${uri.keyspace}")
      session
    }
  }

  case class CassandraConnectionUri(connectionString: String) {
    private val uri = new URI(connectionString)

    private val additionalHosts = Option(uri.getQuery) match {
      case Some(query) => query.split('&').map(_.split('=')).filter(param => param(0) == "host").map(param => param(1)).toSeq
      case None => Seq.empty
    }

    val host = uri.getHost
    val hosts = Seq(uri.getHost) ++ additionalHosts
    val port = uri.getPort
    val keyspace = "sparking"
  }

  def buildOffer(r: Row): Offer = {
    val offer_id = r.getInt("offer_id")
    val offer_name = r.getString("offer_name")
    val score = r.getInt("score")
    Offer(offer_id, offer_name, score)
  }


  def getOffersFromDb(userId: String): String =  {

//    val uri = CassandraConnectionUri("cassandra://172.16.33.16:9042")
    val uri = CassandraConnectionUri("cassandra://localhost:9042")
    println("uri set")
    val session = DatabaseHelper.createSessionAndInitKeyspace(uri)
    println("session set")
    val result = session.execute("SELECT * FROM sparking.offers WHERE user_id='"+userId+"' ALLOW FILTERING;")
    println("query done")
    val resultList = result.all()

//    val resultList = result.all()
    val lengthResults = resultList.size()
    val offerList: List[Offer] = {
      var offers: List[Offer] = Nil
      var a = 0
      // for loop execution with a range
      for (a <- 0 until lengthResults - 1) {
        println(resultList.get(a))
        var offer_id = resultList.get(a).getInt(0)
        var offer_name = resultList.get(a).getString(2)
        val score = resultList.get(a).getDouble(3)
        offers = Offer(offer_id, offer_name, score) :: offers
      }
      println(offers)
      offers
    }
    println(offerList)
    writePretty(offerList)

  }
  def getOffers(userId: String): String =  {
    val belegOffer = Offer(1, "Beleggen", 0.42)
    val spaarOffer = Offer(2, "Sparen", 0.52)
    val leenOffer = Offer(3, "Lenen", 0.32)
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
  path("getOffers" / Segment) { input => //The pathvariable will be /hello/{pathVariable}
    get{
      respondWithHeader(RawHeader("Access-Control-Allow-Origin","*")){
        respondWithMediaType(`text/plain`) {
          complete{
            getOffersFromDb(input)
          }
        }
      }
    }
  }
}
