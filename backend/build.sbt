//
// http://spark.apache.org/docs/latest/quick-start.html#a-standalone-app-in-scala
//
name := """spark-in-action"""

version := "1.0"

// 2.11 doesn't seem to work
scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.spark"  %% "spark-core"    % "1.6.1",
  "org.apache.hadoop"  % "hadoop-client" % "2.6.0"
)

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"


fork in run := true