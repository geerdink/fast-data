/**
  * Ger van Rossum (2016).
  */
package sparking.data

import spray.json.DefaultJsonProtocol

object JsonProtocol extends DefaultJsonProtocol {

  implicit val CustomerDataJsonFormat = jsonFormat2(CustomerData)

}
