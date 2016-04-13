package lambda.batch

import kafka.serializer.StringDecoder
import lambda.LambdaBase
import lambda.util._
import lambda.data._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.classification.LogisticRegressionModel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.mllib.linalg._
import org.apache.spark.mllib.regression.LinearRegressionModel

class SocialMedia extends LambdaBase {
  // initialize Spark Streaming
  val conf = new SparkConf().setAppName("fast-data-social-media").setMaster("local[2]")
  val sc = new SparkContext(conf)
  val ssc = new StreamingContext(conf, Seconds(5)) // batch interval = 5 sec

  // load machine learning model from disk
  val model = LogisticRegressionModel.load(sc, "/home/scala-academy/social_media.model")

  val kafkaParams = Map[String, String]("metadata.broker.list" -> "localhost:9092")
  val kafkaDirectStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
    ssc, kafkaParams, Set("social_media"))

  kafkaDirectStream
    .map(rdd => SocialMediaEventHelper.createSocialMediaEvent(rdd._2))
    .foreachRDD(rdd => rdd.foreach(processSocialMediaEvent))

  ssc.start() // it's necessary to explicitly tell the StreamingContext to start receiving data
  ssc.awaitTermination()  // wait for the job to finish

  def processSocialMediaEvent(sme: SocialMediaEvent): Unit = {
    // store the social media message
    CassandraHelper.insertSocialMediaEvent(sme);
    // feature vector extraction, LabeledPoint: class that represents the features and labels of a data point
    val vector = new DenseVector(Array(sme.userName.hashCode, sme.message.hashCode))
    // get a new prediction for the top user category
    val value = model.predict(vector)
    // store the predicted category value
    val user = new User(sme.userName, UserHelper.getCategory(value))
    CassandraHelper.updateUserCategory(user)
  }

  // TODO: after a certain time interval, run a machine learning algorithm to retrain the model.
  // model.trainOn(trainingData)
  // model.predictOnValues(testData.map(lp => (lp.label, lp.features))).print()
}
