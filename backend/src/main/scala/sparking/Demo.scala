package sparking

import org.apache.spark.streaming.{Seconds, StreamingContext}
import sparking.k_consumer.KafkaConsumerActor
import sparking.rules.RuleEngine

object Demo extends App {

  implicit val system = ActorSystem("sparking-demo")

  val topic = "test"

  private val alert = system.actorOf(KafkaConsumerActor.props("test", RuleEngine.receive))

  println(topic)

  val ssc = new StreamingContext(conf, Seconds(5))

  //val stream = ssc.socketStream("localhost", 7777)

  //stream.

  ssc.start()
  ssc.awaitTermination()

}
