/**
  * Ger van Rossum (2016).
  */
package lambda.util

import java.util.Properties

import com.typesafe.config.{Config, ConfigFactory}
import kafka.consumer.{Consumer, ConsumerConfig}
import kafka.producer.{Producer, ProducerConfig}

/**
  * Read properties from configuration and
  * - Make these accessible as Properties
  * - Allow properties to be overwritten
  * @param config configuration object
  */
private class ConfigProperties(config: Config) extends Properties() {
  override def containsKey(name: AnyRef): Boolean = super.containsKey(name) || config.hasPath(name.toString)

  override def getProperty(name: String): String =
    if (super.containsKey(name)) super.getProperty(name)
    else config.getString(name)
}

class KafkaConnection(path: String) {
  private val config = ConfigFactory.load.getConfig(path)

  def producer(clientId: String = "") = {
    val props = new ConfigProperties(config.getConfig("producer"))
    if (clientId != "") props.put("client.id", clientId)  // clientId == topic
    new Producer[AnyRef, AnyRef](new ProducerConfig(props))
  }

  def consumer(groupId: String = "", timeout: Int = -1) = {
    val props = new ConfigProperties(config.getConfig("consumer"))
    if (groupId != "") props.put("group.id", groupId)  // groupId == topic
    if (timeout > 0) props.put("consumer.timeout.ms", timeout.toString)
    Consumer.create(new ConsumerConfig(props))
  }
}

object KafkaConnection extends KafkaConnection("kafka") {
  def apply(path: String) = new KafkaConnection(path)
}