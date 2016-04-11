package lambda.speed

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import lambda.LambdaBase

object StreamManager extends LambdaBase {
  // initialize Spark Streaming
  val conf = new SparkConf().setAppName("fast-data").setMaster("local[2]")
  val ssc = new StreamingContext(conf, Seconds(3)) // batch interval = 3 sec

  val kafkaParams = Map[String, String]("metadata.broker.list" -> "localhost:9092")
  val kafkaDirectStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
    ssc, kafkaParams, Set("search_history"))

  // process incoming data
  kafkaDirectStream.foreachRDD(rdd => {
    print(" IProcessing incoming RDD, ")
    rdd.foreach(r => {
      println(s" parts: ${r._1}, ${r._2}")
      ProcessEvent(r._2)
    })
  })

  ssc.start() // it's necessary to explicitly tell the StreamingContext to start receiving data
  ssc.awaitTermination()

  // wait for the job to finish

  def ProcessEvent(input: String) = {
    println("rdd value: " + input)
    cassandraWriter ! input
  }
}

