package lambda.domain

case class CarLocationEvent(latitude: Float, longitude: Float)

object CarLocationEventHelper {
  def createCarLocationEvent(input: String): CarLocationEvent = {
    val parts = input.split(',')
    new CarLocationEvent(parts(0).toFloat, parts(1).toFloat)
  }
}
