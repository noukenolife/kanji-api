import Dependencies.scalaTest

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.1.0",
  "com.typesafe.akka" %% "akka-http" % "10.1.0",
  "com.typesafe.akka" %% "akka-stream" % "2.5.11",
  "com.google.inject" % "guice" % "4.0",
  "net.codingwell" %% "scala-guice" % "4.1.1"
)

libraryDependencies ++= Seq(
  "org.scalamock" %% "scalamock" % "4.1.0" % Test,
  scalaTest % Test
)
