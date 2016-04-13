package lambda.util

import java.net.URI

import com.datastax.driver.core.{Cluster, ConsistencyLevel, QueryOptions}
import lambda.ProductScore

object CassandraHelper {
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
    val keyspace = "fastdata"
  }

  def updateScore(productScore: ProductScore): Unit = {
    val uri = CassandraConnectionUri("cassandra://localhost:9042")
    val session = Helper.createSessionAndInitKeyspace(uri)

    val query = "INSERT INTO fastdata.products (user_name, product_category, product_name, score, insertion_time) VALUES " +
      s"('${productScore.userName}', '${productScore.productCategory}', '${productScore.productName}', ${productScore.score}, now())"

    session.execute(query)
  }
}