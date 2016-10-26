package lambda.batch

import kafka.serializer.StringDecoder
import lambda.LambdaBase
import lambda.util._
import lambda.domain._
import lambda.rules.DensityCalculator
import lambda.util.CassandraHelper._
import org.apache.spark.mllib.regression.LinearRegressionModel
import org.apache.spark.mllib.linalg._
import org.apache.spark.sql.SparkSession

class CarParkUpdater extends LambdaBase {

  // initialize Spark for batch processing
  val spark = SparkSession.builder.appName("batch-data").master("local[*]")
    .getOrCreate()

  // get predictive model
  val model = LinearRegressionModel.load(spark.sparkContext, "/opt/models/parking.model")

  // clean up (remove car data older than 60 seconds)
  CassandraHelper.removeOldCarLocations(60)

  // get car park data
  val textFile = spark.sparkContext.textFile("hdfs://data/latest.csv")

  // iterate over all car parks in the data set
  textFile.map(line => CarParkHelper.createCarPark(line)).map{cp =>
    // get recent set of car data (running average)
    val carsInNeighborhood = DensityCalculator.calculateDensity(cp, getCarsInNeighborhood(cp))
    val updatedCarPark = cp.setCarsInNeighborhood(carsInNeighborhood)

    // predict score (machine learning)
    val vector = new DenseVector(updatedCarPark.featureVectorArray)
    val score = model.predict(vector)

    // update feature set and score
    val scoredCarPark = updatedCarPark.setScore(score)
    updateCarParkFeatures(updatedCarPark)
  }

}
