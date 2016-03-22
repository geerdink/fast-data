package sparking.prediction

import org.apache.spark.{SparkConf, SparkContext}

object Predictor {

  val conf = new SparkConf().setAppName("SparkING").setMaster("local[2]")
      .set("spark.cassandra.connection.host", "localhost")

  val sc = new SparkContext("spark://172.13.66.13:8080", "test", conf)

  def HelloWorld(input: String): Unit = {

    // does not do anything yet;
    // TODO: hello world scenario = push input string through Spark engine, store results in Cassandra

    val rdd = sc.parallelize(input.toCharArray)
    println("Number of input chars: " + rdd.count())

  }
}
