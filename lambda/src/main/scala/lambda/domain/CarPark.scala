package lambda.domain

case class CarPark(name: String, latitude: Float, longitude: Float, capacity: Int,
                   usage: Float, accessibility: Float, openFrom: Int, openUntil: Int, rate: Float,
                   cars: Float,
                   score: Float)
{
  def setCarsInNeighborhood(carsInNeighborhood: Float): CarPark = {
    CarPark(name, latitude, longitude, capacity, usage, accessibility, openFrom, openUntil, rate, carsInNeighborhood, score)
  }

  def setScore(score: Float): CarPark = {
    CarPark(name, latitude, longitude, capacity, usage, accessibility, openFrom, openUntil, rate, cars, newScore)
  }
}
