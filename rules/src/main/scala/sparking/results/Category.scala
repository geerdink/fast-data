package sparking.results

import sparking.data.CustomerEvent

/**
  * Created by m07f469 on 23-3-2016.
  */
case class Category(user: String, category: String, score: Double) extends CustomerEvent {
  override def text = s"user=${user},category=${category},score=${score}"
}
