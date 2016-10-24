package lambda.rules

import lambda.domain.{CarLocation, CarPark}

object DensityCalculator {

  def calculateDensity(carPark: CarPark, cars: List[CarLocation]): Float = {
    var latVector = 0F
    var lonVector = 0F

    // TODO: calculate actual distance (trigeometry)
    cars.foreach(car => {
      latVector = latVector + (if (carPark.latitude == car.latitude) 1 else 1 / Math.abs(carPark.latitude - car.latitude))
      lonVector = lonVector + (if (carPark.longitude == car.longitude) 1 else 1 / Math.abs(carPark.longitude - car.longitude))
    })

    latVector + lonVector
  }
}
