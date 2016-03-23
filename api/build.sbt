import sbt.serialization.json

//name := "sparking-api"

//version       := "0.1"
//scalaVersion  := "2.11.6"

//scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

//libraryDependencies ++= {
//  val akkaV = "2.3.9"
//  val sprayV = "1.3.3"
//  Seq(
//    "io.spray"            %%  "spray-can"     % sprayV,
//    "io.spray"            %%  "spray-routing" % sprayV,
//    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
//    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
//    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
//    "org.scalatest" %% "scalatest" % "2.2.6" % "test",
//    "com.datastax.cassandra" % "cassandra-driver-core" % "3.0.0",
//    "org.json4s" %% "json4s-native" % "3.3.0"
//  )
//}

lazy val commonSettings = Seq(
  organization := "gni.kraps",
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.11.7"
)

lazy val api = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "gni.kraps.api",
    crossPaths := false,
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF","io.netty.versions.properties") => MergeStrategy.first
      case PathList("org", "slf4j", "impl", xs @ _*) => MergeStrategy.first
      case x =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
    },
    libraryDependencies ++= {
      val akkaVersion = "2.3.9"
      val sprayVersion = "1.3.3"
      val kafkaVersion = "0.9.0.1"
      Seq(
        "org.apache.kafka" %% "kafka" % kafkaVersion,
//        "org.apache.spark" % "spark-core_2.11" % "1.6.1",
//        "org.apache.spark" % "spark-sql_2.11" % "1.6.1",
        "com.datastax.cassandra" % "cassandra-driver-core" % "3.0.0",
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
        "org.json4s" %% "json4s-native" % "3.3.0"
//        "com.typesafe.play" % "play-json_2.11" % "2.5.0",
//        "com.typesafe.play" % "anorm_2.11" %"2.5.0"
      )
    }
//    resolvers ++= Seq(
//    "Spray repository" at "http://repo.spray.io",
//    "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"
//    )



  )

