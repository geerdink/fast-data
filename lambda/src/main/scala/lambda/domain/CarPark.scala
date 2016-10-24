package lambda.domain

case class CarPark(name: String, latitude: Float, longitude: Float, capacity: Int,
                   usage: Float, accessibility: Float, openFrom: Int, openUntil: Int, rate: Float,
                   cars: Float)
