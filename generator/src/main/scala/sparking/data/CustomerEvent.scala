/**
  * Ger van Rossum (2016).
  */
package sparking.data

object CustomerEvent {

  def split(parts: Array[String])(i: Int): String = {
    parts(i).split('=')(1)
  }

}

trait CustomerEvent {

  val user: String

  def text: String

}
