package lambda.domain

case class ParkingLotScore(name: String, score: Float)

object ParkingLotScoreHelper {
  def createParkingLotScore(input: String): ParkingLotScore = {
    // name=ArenA,score=0.1
    //username=Piet,category=phones,productname=iphone,score=2

    val parts = input.split(',')
    new ParkingLotScore(parts(0).split('=')(1), parts(1).split('=')(1).toFloat)
  }
}
