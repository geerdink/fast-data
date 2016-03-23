import AssemblyKeys._

assemblySettings

name := """SparkING"""

version := "1.0"

scalaVersion := "2.10.6"

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

libraryDependencies ++= {
  val sparkVersion = "1.6.1"
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-graphx" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-streaming-kafka" % sparkVersion
    // "com.datastax.spark" %% "spark-cassandra-connector" % "1.6.0-M1"
    //"com.websudos" %% "phantom-dsl_2.10" % "1.22.0"
  )
}

assemblySettings

jarName in assembly := "sparking.jar"

mergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}

fork in run := true
