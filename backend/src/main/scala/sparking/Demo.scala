package sparking

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.kafka._

object Demo {
  def main(args: Array[String]) {
    println("test 123")

    val conf = new SparkConf().setAppName("SparkING").setMaster("local[2]")
    // .set("spark.cassandra.connection.host", "localhost")

    val ssc = new StreamingContext(conf, Seconds(5)) // batch interval = 5 sec

    val kafkaStream = KafkaUtils.createStream(
      ssc,
      Map("broker.id" -> "9092", "logs.dir" -> "/tmp/kafka-logs", "zookeeper.connect" -> "localhost:2181"),
      Map("sparking" -> 1),
      StorageLevel.MEMORY_AND_DISK)

    println("count = " + kafkaStream.count())

//    val lines = ssc.socketTextStream("localhost", 7000) // create a DStream
//    val errorLines = lines.filter(_.contains("error"))
//    errorLines.foreachRDD(rdd => rdd.foreach(line => println(line)))

    ssc.start()  // it's necessary to explicitly tell the StreamingContext to start receiving data
    ssc.awaitTermination()  // wait for the job to finish



    //val sc = new SparkContext("spark://172.13.66.13:8080", "test", conf)

    println("...")

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
