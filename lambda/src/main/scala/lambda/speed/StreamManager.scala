package lambda.speed

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import akka.actor.ActorSystem
import lambda.util._

object StreamManager {

  def main(args: Array[String]) {
    println(">>> start spark job")

    // initialize Spark Streaming
    val conf = new SparkConf().setAppName("fast-data").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(2)) // batch interval = 2 sec

    val kafkaParams = Map[String, String]("metadata.broker.list" -> "localhost:9092")
    val kafkaDirectStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, Set("page_history"))

    // initialize Cassandra writer actor
    implicit val system = ActorSystem("fast-data")
    val cassandraWriter = system.actorOf(CassandraWriterActor.props("page_history"))

    // process incoming data
    kafkaDirectStream.foreachRDD(rdd => rdd.foreach(r => ProcessEvent(r._2)))
    // TODO: write to Cassandra

    ssc.start() // it's necessary to explicitly tell the StreamingContext to start receiving data
    ssc.awaitTermination() // wait for the job to finish

    def ProcessEvent(input: String) = {
      println("rdd value: " + input)
      cassandraWriter ! input
    }
  }

}
