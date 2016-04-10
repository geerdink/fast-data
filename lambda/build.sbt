import AssemblyKeys._

assemblySettings

organization := "fast-data"

name := """Fast Data in the IoT"""

version := "1.0"

scalaVersion := "2.10.5"

resolvers ++= Seq(
  Resolver.sonatypeRepo("public"),
  Resolver.sbtPluginRepo("releases"),
  Resolver.url("bintray-sbt-plugins", url("http://dl.bintray.com/sbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)
)

libraryDependencies ++= {
  val sparkVersion = "1.6.1"
  val akkaVersion = "2.3.9"
  val sprayVersion = "1.3.3"
  val kafkaVersion = "0.8.2.1"
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-graphx" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-streaming-kafka" % sparkVersion,
    "org.apache.kafka" %% "kafka" % kafkaVersion,
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "io.spray" %% "spray-can" % sprayVersion,
    "io.spray" %% "spray-routing" % sprayVersion,
    "io.spray" %% "spray-testkit" % sprayVersion % "test",
    "io.spray" %% "spray-json" % "1.3.2",
    "org.scalatest" %% "scalatest" % "2.2.6" % "test",
    "ch.qos.logback" % "logback-classic" % "1.1.6",
    "ch.qos.logback" % "logback-core" % "1.1.6",
    "com.datastax.cassandra" % "cassandra-driver-core" % "3.0.0",
    "org.scalatest" %% "scalatest" % "3.0.0-M15"
  )
}

assemblySettings

jarName in assembly := "fast-data.jar"

mergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}

fork in run := true
