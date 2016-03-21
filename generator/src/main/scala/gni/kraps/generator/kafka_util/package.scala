/**
  * Ger van Rossum (2016).
  */
package gni.kraps.generator

import kafka.message.MessageAndMetadata

package object kafka_util {

  type KafkaKeyValue = MessageAndMetadata[String, String]

}
