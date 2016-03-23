package sparking.util

import java.net.URI
import com.datastax.driver.core.{Cluster, QueryOptions, ConsistencyLevel}

object CassandraHelper {
  def createOfferingUpdate(input: String): OfferingUpdate = {
    //user=1,offer=Beleggen,scoreDelta=2.5
    val parts = input.split(',')
    new OfferingUpdate(parts(0).split('=')(1), parts(1).split('=')(1), parts(2).split('=')(1).toDouble)
  }

  object Helper {
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

  def updateOfferInDb(offeringUpdate: OfferingUpdate): Unit = {
    //    val uri = CassandraConnectionUri("cassandra://172.16.33.16:9042")
    val uri = CassandraConnectionUri("cassandra://localhost:9042")
    println("uri set")
    val session = Helper.createSessionAndInitKeyspace(uri)
    println("session set")


    val result = session.execute("UPDATE sparking.offers SET score = score + " + offeringUpdate.scoreDelta + " WHERE user_name='" + offeringUpdate.userId + "' AND offer_name='" + offeringUpdate.offering + "';")
    println("query done")
  }

}