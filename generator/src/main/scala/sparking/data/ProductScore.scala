package sparking.data

import scala.util.Try

object ProductScore {

  import CustomerEvent._

  def parse(text: String): Try[ProductScore] = Try({
    val part = split(text.split('c'))(_)
    ProductScore(part(0), part(1), part(2), Int.unbox(part(3)))
  })
}

case class ProductScore(userName: String, productCategory: String, productName: String, score: Int) extends CustomerEvent {
  override def text: String = s"user_name=${userName},product_category=${productCategory},productName=${productName},score=${score}"
}
