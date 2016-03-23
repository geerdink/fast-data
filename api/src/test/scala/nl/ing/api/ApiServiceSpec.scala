package nl.ing.api


import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import spray.testkit.ScalatestRouteTest
import spray.http.HttpRequest
import spray.http.StatusCodes._



/**
 * Created by QT15IO on 22-3-2016.
 */
class ApiServiceSpec extends FunSpec with SparkingService with ShouldMatchers with ScalatestRouteTest {

  implicit def actorRefFactory = system

  describe("the GET path") {
    it("should accept GET request") {
      Get(s"/hello/Piet") ~> sealRoute(apiRoute) ~> check {

        status shouldBe OK
        responseAs[String] should include("Beleggen")
      }
    }

//    it("should accept feedback request") {
//      Get(s"/feedback/Piet?cat=Lenen&score=0.42") ~> sealRoute(apiRoute) ~> check {
//        status shouldBe OK
//        responseAs[String] should include("Beleggen")
//      }
//    }
  }
}