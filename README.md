# Fast Data in the IoT

This repository contains the following parts of a streaming data solution:
- generator = data generator that produces data on a Kafka bus, built with Scala.
- lambda = data processing with rules (ETL) and machine learning, built with Akka and Spark (Streaming, MLlib). Uses the lambda architecture to separate data flows (batch and speed).
- util = generic Kafka connectors.
