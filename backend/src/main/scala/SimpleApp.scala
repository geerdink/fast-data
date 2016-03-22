import org.apache.spark.{SparkConf, SparkContext}
import com.datastax.spark.connector._

object SimpleApp {
  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("SparkING").setMaster("local[2]")
        .set("spark.cassandra.connection.host", "localhost")

    val sc = new SparkContext(conf)
    // val sc = new SparkContext("spark://172.13.66.13:8080", "test", conf)

    //val logFile = "README.md"

    //val logData = sc.textFile(logFile, 2).cache()
   // val numAs = logData.filter(_.contains("a")).count()
    //val numBs = logData.filter(_.contains("b")).count()

    // for testing
    //println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))
    Pi.Calculate(sc)

    // Hello World scenario: write something to Cassandra
    //val cas = sc.cassandraTable("sparking", "test")
    //println(cas.count())

    // use in local dev mode??
    // sc.stop()
  }
}
