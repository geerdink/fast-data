package lambda.util

import java.net.URI
import com.datastax.driver.core.{Cluster, QueryOptions, ConsistencyLevel}

object CassandraHelper {
  def createProductScore(input: String): ProductScore = {
    //username=Piet,category=phones,productname=iphone,score=2
    val parts = input.split(',')
    new ProductScore(parts(0).split('=')(1), parts(1).split('=')(1), parts(2).split('=')(1), parts(3).split('=')(1).toInt)
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
    val keyspace = "fastdata"
  }

  def updateScore(productScore: ProductScore): Unit = {
    val uri = CassandraConnectionUri("cassandra://localhost:9042")
    val session = Helper.createSessionAndInitKeyspace(uri)

    println("uri and session set")

    val query = "INSERT INTO fastdata.products (user_name, product_category, product_name, score, insertion_time) VALUES " +
      s"('${productScore.userName}', '${productScore.productCategory}', '${productScore.productName}', ${productScore.score}, now())"
    val result = session.execute(query)

    println("query done, result: " + result)
  }
}