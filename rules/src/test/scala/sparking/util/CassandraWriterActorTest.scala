package sparking.util

import akka.actor.ActorSystem

/**
  * Created by Bas on 23-3-2016.
  */
class CassandraWriterActorTest extends UnitSpecBase {
  "An input value" when {
    "giving a correct string" should {
      "result in a correct OfferingUpdate" in {
        val ou = CassandraHelper.createOfferingUpdate("uid=1,offer=Beleggen,delta=2.3")
        ou.userId.shouldBe("1")
        ou.offering.shouldBe("Beleggen")
        ou.scoreDelta.shouldBe(2.3)
      }
    }
  }

}
