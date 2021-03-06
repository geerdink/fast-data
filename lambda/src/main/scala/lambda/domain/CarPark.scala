package lambda.domain

import org.apache.spark.ml.linalg.DenseVector

case class CarPark(name: String, latitude: Float, longitude: Float, capacity: Int,
                   usage: Float, accessibility: Float, openFrom: Int, openUntil: Int, rate: Float,
                   cars: Float,
                   score: Float)
{
  def setCarsInNeighborhood(carsInNeighborhood: Float): CarPark = {
    CarPark(name, latitude, longitude, capacity, usage, accessibility, openFrom, openUntil, rate, carsInNeighborhood, score)
  }

  def setScore(newScore: Double): CarPark = {
    CarPark(name, latitude, longitude, capacity, usage, accessibility, openFrom, openUntil, rate, cars, newScore.toFloat)
  }

  def featureVectorArray = Array(latitude.toDouble, longitude.toDouble, capacity.toDouble, usage.toDouble, accessibility.toDouble, openFrom.toDouble, openUntil.toDouble, rate.toDouble, cars.toDouble)
}

object CarParkHelper {
  /**
    * Get a car park without the cars and score
    */
  def createCarPark(input: String): CarPark = {
    val parts = input.split(',')
    CarPark(parts(0), parts(1).toFloat, parts(2).toFloat, parts(3).toInt, parts(4).toFloat, parts(5).toInt, parts(6).toInt, parts(7).toInt, parts(8).toFloat, 0, 0)
  }
}
