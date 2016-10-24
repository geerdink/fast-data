package lambda.rules

import lambda.domain.{CarLocation, CarPark}
import org.scalatest.FunSpec

class DensityCalculatorTest  extends FunSpec {
  describe("The Density Calculator") {
    it("should create a valid car density vector") {
      val carPark = CarPark("Test", 40, 50, 300, 0.6F, 0.4F, 6, 23, 1.4F, 0.1F)
      val carLocations = List(
        CarLocation("ip1", 41, 55),
        CarLocation("ip2", 60, 30),
        CarLocation("ip3", 72, 63),
        CarLocation("ip4", 12, 15),
        CarLocation("ip5", 42, 24),
        CarLocation("ip6", 62, 72),
        CarLocation("ip7", 91, 49),
        CarLocation("ip8", 40, 50),
        CarLocation("ip9", 23, 53)
      )

      val d = DensityCalculator.calculateDensity(carPark, carLocations)

      println(d)
      assert(d > 0)
    }
  }
}
