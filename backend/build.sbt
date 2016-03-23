import AssemblyKeys._

assemblySettings

name := """SparkING"""

version := "1.0"

scalaVersion := "2.10.4"

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
// resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

resolvers ++= Seq(
  Resolver.sonatypeRepo("public"),
  Resolver.sbtPluginRepo("releases"),
  Resolver.url("bintray-sbt-plugins", url("http://dl.bintray.com/sbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0" % "provided",
  "org.apache.spark" %% "spark-hive" % "1.6.0" % "provided",
  "org.apache.spark" %% "spark-sql" % "1.6.0" % "provided",
  "org.apache.spark" %% "spark-streaming" % "1.6.0" % "provided",
  "org.apache.spark" %% "spark-streaming-kafka" % "1.6.0",
  "org.apache.spark" %% "spark-mllib" % "1.6.0" % "provided",
  "org.apache.spark" %% "spark-graphx" % "1.6.0" % "provided",
  "com.datastax.spark" %% "spark-cassandra-connector" % "1.6.0-M1"
  //"com.websudos" %% "phantom-dsl_2.10" % "1.22.0"
)

//assemblySettings

//assemblyJarName in assembly := "sparking.jar"
//

mergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}

fork in run := true
