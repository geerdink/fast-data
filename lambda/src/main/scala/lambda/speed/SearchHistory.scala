package lambda.speed

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import lambda._
import lambda.data._
import lambda.util.{CassandraHelper, CassandraWriterActor}

object SearchHistory extends LambdaBase {
  // initialize Cassandra writer
  //val cassandraWriter = system.actorOf(CassandraWriterActor.props)

  // initialize Spark Streaming
  val conf = new SparkConf().setAppName("fast-data-search-history").setMaster("local[2]")
  val ssc = new StreamingContext(conf, Seconds(2)) // batch interval = 2 sec

  val kafkaParams = Map[String, String]("metadata.broker.list" -> "localhost:9092")
  val kafkaDirectStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
    ssc, kafkaParams, Set("search_history"))

  // store product scores from search history
  kafkaDirectStream
    .map(rdd => ProductScoreHelper.createProductScore(rdd._2))
    .filter(_.productCategory != "Sneakers")
    .foreachRDD(rdd =>  rdd.foreach(CassandraHelper.insertScore))

    //.foreachRDD(rdd => rdd.foreach(cassandraWriter ! CassandraHelper.updateScore(_)))

  ssc.start() // it's necessary to explicitly tell the StreamingContext to start receiving data
  ssc.awaitTermination()  // wait for the job to finish
}
