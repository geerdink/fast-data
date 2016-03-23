//addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.0-RC1")
//addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.2")

resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Spray Repository" at "http://repo.spray.cc/"

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.10.2")
