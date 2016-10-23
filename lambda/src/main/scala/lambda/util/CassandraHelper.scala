package lambda.util

import java.net.URI
import com.datastax.driver.core.{Cluster, ConsistencyLevel, QueryOptions}
import lambda.domain._

object CassandraHelper {
  val uri = CassandraConnectionUri("cassandra://localhost:9042")
  val session = createSessionAndInitKeyspace(uri)

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

  def insertScores(parkingLotScores: List[ParkingLotScore]): Unit = {
    var query = ""
    parkingLotScores.foreach(parkingLotScore => query += "INSERT INTO fastdata.parkingLotScores (name, score, insertion_time) VALUES " +
      s"('${parkingLotScore.name}', ${parkingLotScore.score}, now()); ")

    session.execute(query)
  }

  def insertScore(parkingLotScore: ParkingLotScore): Unit = {
    val query = "INSERT INTO fastdata.parkingLotScores (name, score, insertion_time) VALUES " +
      s"('${parkingLotScore.name}', ${parkingLotScore.score}, now())"

    session.execute(query)
  }
//
//  def insertSocialMediaEvent(socialMediaEvent: CarLocationEvent): Unit = {
//    val query = s"INSERT INTO fastdata.social (user_name, message) VALUES ('${socialMediaEvent.userName}', '${socialMediaEvent.message}', now())"
//
//    session.execute(query)
//  }
//
//  def updateUserCategory(user: ParkingLot): Unit = {
//    val query = s"UPDATE fastdata.users SET top_product_category = '${user.capacity}', update_time = now() WHERE user_name = '${user.name}'"
//
//    session.execute(query)
//  }
}
