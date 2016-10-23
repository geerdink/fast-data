package lambda.rules

import lambda.domain.CarLocationEvent

class LocationFilter {
  def filterLocation(carLoc: CarLocationEvent): Boolean = {
    carLoc.latitude > 11 && carLoc.latitude < 19 &&
    carLoc.longitude > 50 && carLoc.longitude < 60
  }
}
