package etl

import java.net.URI

import com.datastax.driver.core.{Cluster, ConsistencyLevel, QueryOptions}
import etl.domain.{Customer, CustomerHelper, Order}
import lambda.util.CassandraHelper.CassandraConnectionUri
import org.apache.spark.sql.SparkSession

object BatchEtl {
  def main(args: Array[String]) {

    // ------- EXTRACT ------- //

    // initialize Spark for batch processing
    val spark = SparkSession.builder
      .appName("spark-etl")
      .master("local[*]")
      .getOrCreate()

    // get customer data from HDFS in Spark SQL Dataset
    val rawCustomers = spark.read
      .textFile("hdfs://data/customers.csv")

    // get order data from HDFS in Spark SQL Dataset
    val rawOrders = spark.read
      .textFile("hdfs://data/orders.csv")

    // cache to reuse the dataset
    val customerData = rawCustomers.cache()
    val orderData = rawOrders.cache()

    // ------- TRANSFORM ------- //

    // get business classes
    import spark.sqlContext.implicits._
    val customers = customerData.as[Customer].as("CUSTOMERS")
    val orders = orderData.as[Order].as("ORDERS")

    val records = customers
      // only load premium customer over 18
      .filter(_.premium)
      .filter(_.age > 18)

      // combine with orders, creates a dataset of tuples (customer, order)
      .joinWith(orders, $"ORDERS.customerId" === $"CUSTOMERS.id", "left outer")

    // calculate total price
    val total = records.map(r => r._2.amount * _._2.product.price).reduce(_ + _)

    // output in a nice format
    records
      .map(r => s"${r._1.name} has ordered ${r._2.amount} units of ${r._2.product.name}s, for a total price of $total")

    // ------- LOAD ------- //
    // copy of CassandraHelper class, to show explicitly here


    // CQL


    // set up Cassandra session
    val uri = new URI("cassandra://localhost:9042")
    val cluster = new Cluster.Builder()
      .addContactPoints(Seq(uri.getHost))
      .withPort(uri.getPort).withQueryOptions(new QueryOptions()
      .setConsistencyLevel(QueryOptions.DEFAULT_CONSISTENCY_LEVEL)).build

    // connect to the keyspace
    val session = cluster.connect
    session.execute("USE etl_example")

    // write record
    def log(record: (Customer, Order)) = {
      session.execute(s"INSERT INTO etl_example.orders (customer_name, amount, product, insertion_time) " +
        s"VALUES ('${record._1.name}', ${record._2.amount}, ${record._2.product}, now());")
    }

    // stop batch
    spark.stop()
  }
}