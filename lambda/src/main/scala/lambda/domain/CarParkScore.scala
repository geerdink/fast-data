package lambda.domain

case class CarParkScore(name: String, score: Float)

object CarParkScoreHelper {
  def createParkingLotScore(input: String): CarParkScore = {
    // name=ArenA,score=0.1

    val parts = input.split(',')
    new CarParkScore(parts(0).split('=')(1), parts(1).split('=')(1).toFloat)
  }
}
