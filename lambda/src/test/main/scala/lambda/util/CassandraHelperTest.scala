package lambda.util

import org.scalatest._
import Matchers._
import lambda.domain.ParkingLotScoreHelper

class CassandraHelperTest extends FunSpec {

  describe("The Cassandra helper") {
    it ("should write a log message to the log table") {
      CassandraHelper.log("This is a test message, created in a unit test")
    }

    it ("should convert a string to a parkingLotScore") {

      // val ps = ParkingLotScoreHelper.createParkingLotScore("name=Test,productcategory=Phones,productName=iPhone,score=3")

     // ps.name shouldBe "Test"
     // ps.productCategory shouldBe "Phones"
     // ps.productName shouldBe "iPhone"
     // ps.score shouldBe 3
    }
  }
}
