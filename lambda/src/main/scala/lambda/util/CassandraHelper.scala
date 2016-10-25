package lambda.util

import java.net.URI
import java.util

import com.datastax.driver.core._
import com.datastax.driver.mapping.MappingManager
import lambda.domain._
import org.slf4j.{Logger, LoggerFactory}

object CassandraHelper {
  val log = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)
  val uri = CassandraConnectionUri("cassandra://localhost:9042")
  val session = createSessionAndInitKeyspace(uri)
  val mm = new MappingManager(session)

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

  case class CassandraConnectionUri(connectionString: String) {
    private val uri = new URI(connectionString)

    private val additionalHosts = Option(uri.getQuery) match {
      case Some(query) => query.split('&').map(_.split('=')).filter(param => param(0) == "host").map(param => param(1)).toSeq
      case None => Seq.empty
    }

    val host = uri.getHost
    val hosts = Seq(uri.getHost) ++ additionalHosts
    val port = uri.getPort
    val keyspace = "fastdata"
  }

  def log(s: String) = {
    session.execute(s"INSERT INTO fastdata.log (log, insertion_time) VALUES ('$s', now());")
  }

  def insertScores(parkingLotScores: List[CarParkScore]): Unit = {
    var query = ""
    parkingLotScores.foreach(parkingLotScore => query += "INSERT INTO fastdata.parkingLotScores (name, score, insertion_time) VALUES " +
      s"('${parkingLotScore.name}', ${parkingLotScore.score}, now()); ")

    session.execute(query)
  }

  def insertScore(parkingLotScore: CarParkScore): Unit = {
    val query = "INSERT INTO fastdata.parkingLotScores (name, score, insertion_time) VALUES " +
      s"('${parkingLotScore.name}', ${parkingLotScore.score}, now())"

    session.execute(query)
  }

  def insertCarLocation(cle: CarLocation): Unit = {
    // INSERT is an upsert; it creates new records or updates old ones
    val query = "INSERT INTO fastdata.cars (ipaddress, latitude, longitude, update_time) VALUES " +
      s"('${cle.ipAddress}', ${cle.latitude}, ${cle.longitude}, now())"

    session.execute(query)
  }

  def insertCarParkFeatures(carPark: CarPark): Unit = {
    val query = "INSERT INTO fastdata.carparkfeatures (name, latitude, longitude, capacity, usage, accessibility, openFrom, openUntil, rate, cars, score, update_time) VALUES " +
      s"('${carPark.name}', ${carPark.latitude}, ${carPark.longitude}, ${carPark.capacity}, " +
      s"${carPark.usage}, ${carPark.accessibility}, ${carPark.openFrom}, ${carPark.openUntil}, ${carPark.rate}, " +
      s"${carPark.cars}, ${carPark.score}, now())"

    session.execute(query)
  }

  def updateCarParkFeatures(carPark: CarPark): Unit = {
    val query = s"UPDATE fastdata.carparkfeatures SET latitude=${carPark.latitude}, longitude=${carPark.longitude}, capacity=${carPark.capacity}, usage=${carPark.usage}, " +
      s"accessibility=${carPark.accessibility}, openFrom=${carPark.openFrom}, openUntil=${carPark.openUntil}, rate=${carPark.rate}, " +
      s"cars = ${carPark.cars}, score=${carPark.score}, update_time=now() WHERE name = $name"
  }

  def getCarParkFeatures: List[CarPark] = {
    val query = "SELECT * FROM fastdata.carparkfeatures"

    val results = session.execute(query).all

    //    def createList(r: java.util.List[Row], iter: List[CarPark]): List[CarPark] = {
    //      if (r.isEmpty) iter
    //      else createList(r.)
    //    }

    val carParks = new util.ArrayList[CarPark]()
    results.iterator().forEachRemaining(carParks.add(CarPark(
      _.getString(0)
    )))

    carParks
    // TODO: use mapping
  }

//
//  def insertSocialMediaEvent(socialMediaEvent: CarLocation): Unit = {
//    val query = s"INSERT INTO fastdata.social (user_name, message) VALUES ('${socialMediaEvent.userName}', '${socialMediaEvent.message}', now())"
//
//    session.execute(query)
//  }
//
//  def updateUserCategory(user: CarPark): Unit = {
//    val query = s"UPDATE fastdata.users SET top_product_category = '${user.capacity}', update_time = now() WHERE user_name = '${user.name}'"
//
//    session.execute(query)
//  }
}
