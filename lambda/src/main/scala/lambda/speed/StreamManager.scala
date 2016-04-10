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
    implicit val system = ActorSystem("sparking-demo")
    val cassandraWriter = system.actorOf(CassandraWriterActor.props("page_history"))

    // process incoming data
    val count = kafkaDirectStream.count()
    println(">>> count = " + count)
    count.print()

    // TOD: write to Cassandra

    //    val lines = ssc.socketTextStream("localhost", 7000) // create a DStream
    //    val errorLines = lines.filter(_.contains("error"))
    //    errorLines.foreachRDD(rdd => rdd.foreach(line => println(line)))

    ssc.start() // it's necessary to explicitly tell the StreamingContext to start receiving data
    ssc.awaitTermination() // wait for the job to finish

    println(">>> awaiting termination...")


    // implicit val system = ActorSystem("sparking-demo")
    // val topic = "test"
    //    val alert = system.actorOf(KafkaConsumerActor.props("test", RuleEngine.receive))
    // println(topic)

    //val stream = ssc.socketStream("localhost", 7777)

    //stream.

    //ssc.start()
    //ssc.awaitTermination()
  }

}
