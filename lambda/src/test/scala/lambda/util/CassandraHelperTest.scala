package lambda.util

import org.scalatest._
import Matchers._
import lambda.util.CassandraHelper

/**scalatest
  * Created by scala-academy on 4/10/16.
  */
class CassandraHelperTest extends FunSpec {

  describe("The Cassandra helper") {
    it ("should convert a string to a ProductScore") {
      val ps = CassandraHelper.createProductScore("name=Test,productcategory=Phones,productName=iPhone,score=3")

      ps.userName shouldBe "Test"
      ps.productCategory shouldBe "Phones"
      ps.productName shouldBe "iPhone"
      ps.score shouldBe 3
    }
  }
}
