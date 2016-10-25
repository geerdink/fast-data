package lambda.speed

import org.apache.spark.SparkConf
import lambda._
import lambda.domain._
import lambda.rules.{DensityCalculator, Location}
import lambda.util.CassandraHelper
import lambda.util.CassandraHelper._
import org.apache.spark.mllib.linalg.DenseVector
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ParkingGuide extends LambdaBase {
  val log = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)
  log.info("Fast data application started.")

  CassandraHelper.log("Started!")

  // ------- DATA SCIENCE ------- //

  import org.apache.spark.mllib.regression.LinearRegressionModel
  val model = LinearRegressionModel.load(ssc.sparkContext, "/opt/models/parking.model")

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
    .filter(Location.filterLocalArea)    // only select cars in local area

  // store car locations (update or create)
  filtered
    .foreachRDD(rdd => rdd.foreach(cle => { insertCarLocation(cle) }))

  // recalculate the distribution of each car park in the neighborhood
  filtered
    // get feature set
    .map(cl => Location.getLocalCarParks(cl))
    .foreachRDD(rdd => rdd.foreach(carParks => carParks.foreach { cp =>
      // combine event with previous events (running average)
      val carsInNeighborhood = DensityCalculator.calculateDensity(cp, getCarsInNeighborhood(cp))
      val updatedCarPark = cp.setCarsInNeighborhood(carsInNeighborhood)

      // predict score (machine learning)
      val vector = new DenseVector(updatedCarPark.featureVectorArray)
      val score = model.predict(vector)

      // update feature set and score
      val scoredCarPark = updatedCarPark.setScore(score)
      updateCarParkFeatures(updatedCarPark)
    }
  ))

  ssc.start() // tell the StreamingContext to start receiving data
  ssc.awaitTermination()  // wait for the job to finish
}
