package lambda.domain

case class CarLocation(ipAddress: String, latitude: Float, longitude: Float)

object CarLocationHelper {
  def createCarLocation(input: String): CarLocation = {
    val parts = input.split(',')
    CarLocation(parts(0), parts(1).toFloat, parts(2).toFloat)
  }
}
