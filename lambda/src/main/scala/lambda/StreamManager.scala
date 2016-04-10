package lambda

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka._
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamManager {

  def main(args: Array[String]) {
    println(">>> start spark job")

    val conf = new SparkConf().setAppName("fast-data").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(5)) // batch interval = 5 sec

    val kafkaParams = Map[String, String](
      "metadata.broker.list" -> "localhost:9092"
    )
    val kafkaDirectStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, Set("page_history"))

    val count = kafkaDirectStream.count()
    println(">>> count = " + count)
    count.print()

    //    val lines = ssc.socketTextStream("localhost", 7000) // create a DStream
    //    val errorLines = lines.filter(_.contains("error"))
    //    errorLines.foreachRDD(rdd => rdd.foreach(line => println(line)))

    ssc.start() // it's necessary to explicitly tell the StreamingContext to start receiving data
    ssc.awaitTermination() // wait for the job to finish

    //val sc = new SparkContext("spark://172.13.66.13:8080", "test", conf)

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
