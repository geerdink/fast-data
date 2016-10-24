package lambda.speed

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.{Seconds, StreamingContext}
import lambda._
import lambda.domain._
import lambda.rules.LocationFilter
import lambda.util.{CassandraHelper, CassandraWriterActor}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.mllib.regression.LinearRegressionModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ParkingGuide extends LambdaBase {
  val log = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)
  log.info("Fast data application started.")

  CassandraHelper.log("Started!")

  // initialize Cassandra writer
  //val cassandraWriter = system.actorOf(CassandraWriterActor.props)

  // initialize Spark Streaming
  val conf = new SparkConf().setAppName("fast-data").setMaster("local[*]")
  val ssc = new StreamingContext(conf, Seconds(2)) // batch interval = 2 sec

//  val lines = ssc.socketTextStream("localhost", 9999)
//  val words = lines.flatMap(_.split(" "))
//  val pairs = words.map(word => (word, 1))
//  val counts = pairs.reduceByKey(_ + _)
//  counts.print

//  val kafkaParams = Map[String, String]("metadata.broker.list" -> "localhost:9092")
//  val kafkaDirectStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
//    ssc, kafkaParams, Set("cars"))
//
//  // store product scores from search history
//  kafkaDirectStream
//    .map(rdd => ProductScoreHelper.createProductScore(rdd._2))
//    .filter(_.productCategory != "Sneakers")
//    .foreachRDD(rdd =>  rdd.foreach(CassandraHelper.insertScore))
//
//    //.foreachRDD(rdd => rdd.foreach(cassandraWriter ! CassandraHelper.updateScore(_)))


  // 1. get event data from sensors (cars)
  val topics = Array("cars")
  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "example",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  val stream = KafkaUtils.createDirectStream[String, String](
    ssc,
    PreferConsistent,
    Subscribe[String, String](topics, kafkaParams))

  // 2. apply business rules (filter)
  val filtered = stream
    .map(event => CarLocationHelper.createCarLocation(event.value))
    //.filter(LocationFilter.filterLocation(_))

     // .foreachRDD(rdd => rdd.foreach())

    // recalculate the distribution of each car park in the neighborhood


    // store car locations (update or create) AND update car park features
      .foreachRDD(rdd => rdd.foreach(cle => {

        CassandraHelper.insertCarLocation(cle)
      }))



  // 3. enrich events -> create feature set


  // 4. predict capacity for each parking lot (score feature sets)
  // load machine learning model from disk
  //val model = LinearRegressionModel.load(ssc, "/home/models/parking.model")

  // 5. update scores in database

//  stream
//    .map(event => CarParkScoreHelper.createParkingLotScore(event.value))    // DStream[CarParkScore]
//    .foreachRDD(rdd => rdd.foreach(score => CassandraHelper.log(score.name + " = " + score.score)))
//
//  // visualize
   // filtered.print
//
//  stream.foreachRDD(rdd => log.info("RDD size = " + rdd.collect.size)) //.foreach(r => log.info(r)))

//  stream.reduceByWindow((lat:Float, long: Float) => lat , Seconds(30))

  // start stream
  ssc.start() // it's necessary to explicitly tell the StreamingContext to start receiving data
  ssc.awaitTermination()  // wait for the job to finish
}
