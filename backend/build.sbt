//
// http://spark.apache.org/docs/latest/quick-start.html#a-standalone-app-in-scala
//
name := """SparkING"""

version := "1.0"

scalaVersion := "2.10.4"
//
//resolvers ++= Seq(
//  "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
//  "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/",
//  "Sonatype repo"                    at "https://oss.sonatype.org/content/groups/scala-tools/",
//  "Sonatype releases"                at "https://oss.sonatype.org/content/repositories/releases",
//  "Sonatype snapshots"               at "https://oss.sonatype.org/content/repositories/snapshots",
//  "Sonatype staging"                 at "http://oss.sonatype.org/content/repositories/staging",
//  "Java.net Maven2 Repository"       at "http://download.java.net/maven/2/",
//  "Twitter Repository"               at "http://maven.twttr.com"
// // Resolver.bintrayRepo("websudos", "oss-releases")
//)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0",
  "org.apache.spark" %% "spark-hive" % "1.6.0",
  "org.apache.spark" %% "spark-sql" % "1.6.0",
  "org.apache.spark" %% "spark-streaming" % "1.6.0",
  "org.apache.spark" %% "spark-streaming-kafka" % "1.6.0",
  "org.apache.spark" %% "spark-mllib" % "1.6.0",
  "org.apache.spark" %% "spark-graphx" % "1.6.0",
  "com.datastax.spark" %% "spark-cassandra-connector" % "1.6.0-M1"
  //"com.websudos" %% "phantom-dsl_2.10" % "1.22.0"
)

// resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

fork in run := true
