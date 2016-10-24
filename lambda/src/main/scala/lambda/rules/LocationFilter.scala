package lambda.rules

import lambda.domain.CarLocation

object LocationFilter {
  def filterLocation(carLoc: CarLocation): Boolean = {
    carLoc.latitude > 1 && carLoc.latitude < 190 &&
    carLoc.longitude > 5 && carLoc.longitude < 60
  }
}
