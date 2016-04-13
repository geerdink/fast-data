/**
  * Ger van Rossum (2016).
  */
package lambda.util

import kafka.producer.KeyedMessage

case class KafkaProducer(topic: String) {

  private val producer = KafkaConnection.producer()

  private def kafkaMesssage(message: Array[Byte], partition: Array[Byte]): KeyedMessage[AnyRef, AnyRef] = {
    if (partition == null) {
      new KeyedMessage(topic, message)
    } else {
      new KeyedMessage(topic, partition, message)
    }
  }

  private def send(message: Array[Byte], partition: Array[Byte]): Unit = {
    try {
      producer.send(kafkaMesssage(message, partition))
    } catch {
      case e: Exception =>
        e.printStackTrace
        System.exit(1)
    }
  }

  def send(message: String, partition: String): Unit =
    send(message.getBytes("UTF8"), partition.getBytes("UTF8"))

  def send(message: String): Unit =
    send(message.getBytes("UTF8"), null)

}
