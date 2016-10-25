package lambda.rules

import lambda.domain.{CarLocation, CarPark}
import lambda.util.CassandraHelper

object Location {
  def filterLocalArea(carLoc: CarLocation): Boolean = {
    carLoc.latitude > 1 && carLoc.latitude < 190 &&
    carLoc.longitude > 5 && carLoc.longitude < 60
  }

  def getLocalCarParks(carLoc: CarLocation): List[CarPark] = {
    def iter(remaining: List[CarPark], list: List[CarPark]): List[CarPark] = {
      if (remaining.isEmpty)
        list
      else if (Math.abs(remaining.head.latitude - carLoc.latitude) < 10 && Math.abs(remaining.head.longitude - carLoc.longitude) < 10)
        iter(remaining.tail, list ++ remaining.head)
      else
        iter(remaining.tail, list)
    }

    iter(CassandraHelper.getCarParkFeatures, List())
  }
}
