package lambda.data

case class SocialMediaEvent(userName: String, message: String)

object SocialMediaEventHelper {
  def createSocialMediaEvent(input: String): SocialMediaEvent = {
    val parts = input.split(',')
    new SocialMediaEvent(parts(0).split('=')(1), parts(1).split('=')(1))
  }
}
