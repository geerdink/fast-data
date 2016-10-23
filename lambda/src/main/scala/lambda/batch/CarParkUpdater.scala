package lambda.batch

import kafka.serializer.StringDecoder
import lambda.LambdaBase
import lambda.util._
import lambda.domain._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.regression._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.mllib.linalg._

class CarParkUpdater extends LambdaBase {



  // initialize Spark MLlib and Streaming
  val conf = new SparkConf().setAppName("fast-data-social-media").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val ssc = new StreamingContext(conf, Seconds(5)) // batch interval = 5 sec


  val kafkaParams = Map[String, String]("metadata.broker.list" -> "localhost:9092")
  val kafkaTopics = Array("test", "social_media")
  val kafkaDirectStream = KafkaUtils.createDirectStream[String, String](ssc, PreferConsistent, Subscribe[String, String](kafkaTopics, kafkaParams))

  kafkaDirectStream
    .map(rdd => CarLocationEventHelper.createCarLocationEvent(rdd.value()))
    .foreachRDD(rdd => rdd.foreach(processSocialMediaEvent))

  ssc.start() // it's necessary to explicitly tell the StreamingContext to start receiving data
  ssc.awaitTermination()  // wait for the job to finish

  def processSocialMediaEvent(sme: CarLocationEvent): Unit = {
    // store the social media message
    //CassandraHelper.insertSocialMediaEvent(sme);
    // feature vector extraction, LabeledPoint: class that represents the features and labels of a data point
    //val vector = new DenseVector(Array(sme.userName.hashCode, sme.message.hashCode))
    // get a new prediction for the top user category
    //val value = model.predict(vector)
    // store the predicted category value
    //val user = new CarPark(sme.userName, ParkingLotHelper.getCategory(value))
    //CassandraHelper.updateUserCategory(user)
  }

  // after a certain time interval, run a machine learning algorithm to retrain the model.
  // model.trainOn(trainingData)
  // model.predictOnValues(testData.map(lp => (lp.label, lp.features))).print()
}
