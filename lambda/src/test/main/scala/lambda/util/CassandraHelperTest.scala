package lambda.util

import org.scalatest._
import lambda.domain.{CarLocationHelper}

class CassandraHelperTest extends FunSpec {

  describe("The Cassandra helper") {
    it ("should write a log message to the log table") {
      CassandraHelper.log("This is a test message, created in a unit test")
    }

    it ("should write a car location to the cars table") {
      val cle = CarLocationHelper.createCarLocation("10.34.25.15,11.45,39.38")
      CassandraHelper.insertCarLocation(cle)
    }

    it ("should get a list of the car park features") {
      val features = CassandraHelper.getCarParkFeatures

      features.foreach(f => println(f.name))
      assert (features.size > 0)
    }
  }
}
