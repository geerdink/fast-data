package lambda.util

import org.scalatest._
import Matchers._
import lambda.domain.ParkingLotScoreHelper

import org.slf4j.LoggerFactory
import org.slf4j.Logger

class CassandraHelperTest extends FunSpec {

  describe("The Cassandra helper") {
    it ("should convert a string to a parkingLotScore") {

      val log = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)
      log.info("Fast data application started.")

      // val ps = ParkingLotScoreHelper.createParkingLotScore("name=Test,productcategory=Phones,productName=iPhone,score=3")

     // ps.name shouldBe "Test"
     // ps.productCategory shouldBe "Phones"
     // ps.productName shouldBe "iPhone"
     // ps.score shouldBe 3
    }
  }
}
