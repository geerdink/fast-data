//
// http://spark.apache.org/docs/latest/quick-start.html#a-standalone-app-in-scala
//
name := """SparkING"""

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.1",
  "org.apache.spark" %% "spark-hive" % "1.6.1",
  "org.apache.spark" %% "spark-sql" % "1.6.1",
  "org.apache.spark" %% "spark-streaming" % "1.6.1",
  "org.apache.spark" %% "spark-streaming-kafka" % "1.6.1",
  "org.apache.spark" %% "spark-mllib" % "1.6.1",
  "org.apache.spark" %% "spark-graphx" % "1.6.1"
)

//libraryDependencies ++= Seq(
//  "org.apache.spark"  %% "spark-core"    % "1.6.1",
//  "org.apache.hadoop"  % "hadoop-client" % "2.6.0"
//)

// resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

fork in run := true
