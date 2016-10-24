package lambda.util

import org.scalatest._
import lambda.domain.{CarLocationEventHelper}

class CassandraHelperTest extends FunSpec {

  describe("The Cassandra helper") {
    it ("should write a log message to the log table") {
      CassandraHelper.log("This is a test message, created in a unit test")
    }

    it ("should write a car location to the cars table") {
      val cle = CarLocationEventHelper.createCarLocationEvent("10.34.25.15,11.45,39.38")
      CassandraHelper.insertCarLocation(cle)
    }
  }
}
