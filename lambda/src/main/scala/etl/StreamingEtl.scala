package etl

import etl.domain.Customer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by bas on 10/26/16.
  */
object StreamingEtl {
  def main(args: Array[String]) {

    // initialize Spark Streaming
    val conf = new SparkConf().setAppName("fast-data").setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(5))

    val stream = ssc.fileStream("hdfs://data/customers.csv")

    // get business classes
    // val customers = customerData.as[Customer].as("CUSTOMERS")


    ssc.start() // tell the StreamingContext to start receiving data
    ssc.awaitTermination()  // wait for the job to finish
  }
}
