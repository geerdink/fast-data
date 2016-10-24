package lambda.domain

case class CarLocationEvent(ipAddress: String, latitude: Float, longitude: Float)

object CarLocationEventHelper {
  def createCarLocationEvent(input: String): CarLocationEvent = {
    val parts = input.split(',')
    new CarLocationEvent(parts(0), parts(1).toFloat, parts(2).toFloat)
  }
}
