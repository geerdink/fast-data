package lambda.data

import scala.util.Try

object SocialMediaEvent {

  import CustomerEvent._

  def parse(text: String): Try[ProductScore] = Try({
    val part = split(text.split('c'))(_)
    ProductScore(part(0), part(1), part(2), Int.unbox(part(3)))
  })
}

case class SocialMediaEvent(userName: String, message: String) extends CustomerEvent {
  override def text: String = s"user_name=${userName},message=${message}"
}
