/**
  * Ger van Rossum (2016).
  */
package gni.kraps.generator.kafka_util

import java.util.Properties

import kafka.message.{DefaultCompressionCodec, NoCompressionCodec}
import kafka.producer.{KeyedMessage, Producer, ProducerConfig}
import spray.json.JsValue

case class JsonProducer(id: String, topic: String, brokers: String) {

  val synchronously = false
  val compress = false
  val batchSize = 1
  val messageSendMaxRetries = 3
  val requestRequiredAcks: Integer = -1

  val props = new Properties()
  val codec = if (compress) DefaultCompressionCodec.codec else NoCompressionCodec.codec

  //props.put("compression.codec", codec.toString)
  props.put("producer.type", if (synchronously) "sync" else "async")
  props.put("metadata.broker.list", brokers)
  props.put("batch.num.messages", batchSize.toString)
  props.put("message.send.max.retries", messageSendMaxRetries.toString)
  props.put("request.required.acks", requestRequiredAcks.toString)
  props.put("client.id", id)

  val producer = new Producer[AnyRef, AnyRef](new ProducerConfig(props))

  private def kafkaMesssage(message: Array[Byte]): KeyedMessage[AnyRef, AnyRef] = {
    new KeyedMessage(topic, message)
  }

  def send(message: Array[Byte]): Unit = {
    try {
      producer.send(kafkaMesssage(message))
    } catch {
      case e: Exception =>
        e.printStackTrace()
        System.exit(1)
    }
  }

  def send(message: JsValue): Unit = send(message.toString.getBytes("UTF8"))

}
