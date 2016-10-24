package lambda.domain

import lambda.util.CassandraHelper
import org.scalatest.FunSpec

class CarParkTest extends FunSpec {
  describe("The Car Park features") {
    it("should be created by the Cassandra helper") {
      val cpf1 = CarPark("Olympic Stadium", 45, 55, 800, 0, 0.3F, 8, 23, 3.2F, 0)
      val cpf2 = CarPark("Sloterdijk", 20, 65, 1000, 0, 0.8F, 7, 24, 2.8F, 0)
      val cpf3 = CarPark("Bos en Lommer", 30, 70, 1500, 0, 0.6F, 6, 23, 1.8F, 0)

      CassandraHelper.insertCarParkFeatures(cpf1)
      CassandraHelper.insertCarParkFeatures(cpf2)
      CassandraHelper.insertCarParkFeatures(cpf3)
    }
  }
}

