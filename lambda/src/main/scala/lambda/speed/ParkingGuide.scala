package lambda.speed

import org.apache.spark.SparkConf
import lambda._
import lambda.domain._
import lambda.rules.Location
import lambda.util.{CassandraHelper}
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ParkingGuide extends LambdaBase {
  val log = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)
  log.info("Fast data application started.")

  CassandraHelper.log("Started!")

  // ------- DATA SCIENCE ------- //

  import org.apache.spark.mllib.regression.LinearRegressionModel
  val model = LinearRegressionModel.load(ssc, "/home/models/parking.model")

  // ------- KAFKA ------- //

  import org.apache.spark.streaming.{Seconds, StreamingContext}
  import org.apache.spark.streaming.kafka010._
  import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
  import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

  // initialize Spark Streaming
  val conf = new SparkConf().setAppName("fast-data").setMaster("local[*]")
  val ssc = new StreamingContext(conf, Seconds(1)) // batch interval = 1 sec

  // set parameters for Kafka connection
  val topics = Array("cars")
  val kafkaParams = Map[String, Object]("bootstrap.servers" -> "localhost:9092")

  // get events in a Spark DStream
  val stream = KafkaUtils.createDirectStream[String, String](
    ssc,
    PreferConsistent,
    Subscribe[String, String](topics, kafkaParams))

  // ------- STREAM ------- //

  // change raw data to business events
  val events = stream
    .map(event => CarLocationHelper.createCarLocation(event.value))

  // apply business rules
  val filtered = events
    .filter(Location.filterLocalArea(_))    // only select cars in local area

  // store car locations (update or create)
  filtered
    .foreachRDD(rdd => rdd.foreach(cle => { CassandraHelper.insertCarLocation(cle) }))

  // recalculate the distribution of each car park in the neighborhood
  filtered
    // get feature set
    .map(cl => Location.getLocalCarParks(cl))
    .foreachRDD(rdd => rdd.foreach(carParks => carParks.foreach { cp =>
      // combine event with previous events (running average)
      val carsInNeighborhood = cp.cars + CassandraHelper.getCarsInNeighborhood(cp)
      val updatedCarPark = cp.setCarsInNeighborhood(carsInNeighborhood)

      // predict  score (machine learning)
      val score = model.predict(Vector(updatedCarPark))

      // update feature set and score
      val scoredCarPark = updatedCarPark.
      CassandraHelper.updateCarParkFeatures(updatedCarPark)
    }
  ))

  // update car park features

  // 3. enrich events -> create feature set

  // ------- DATA SCIENCE ------- //

  // 4. predict capacity for each parking lot (score feature sets)
  // load machine learning model from disk
  //val model = LinearRegressionModel.load(ssc, "/home/models/parking.model")

  // ------- CASSANDRA ------- //

  // update scores in database

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
