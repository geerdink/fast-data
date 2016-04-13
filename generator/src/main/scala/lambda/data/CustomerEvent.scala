/**
  * Ger van Rossum (2016).
  */
package lambda.data

object CustomerEvent {

  def split(parts: Array[String])(i: Int): String = {
    parts(i).split('=')(1)
  }

}

trait CustomerEvent {

  val userName: String

  def text: String
}
