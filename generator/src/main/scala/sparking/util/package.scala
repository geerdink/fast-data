/**
  * Ger van Rossum (2016).
  */
package sparking

import kafka.message.MessageAndMetadata

package object util {

  type KafkaKeyValue = MessageAndMetadata[String, String]

}
