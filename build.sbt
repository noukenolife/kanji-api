import Dependencies._

scalacOptions += "-Ypartial-unification"
resolvers += Resolver.bintrayRepo("hseeberger", "maven")

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "kanji-api",
    libraryDependencies ++= Seq(
      "org.json4s" %% "json4s-native" % "3.5.3",
      "de.heikoseeberger" %% "akka-http-json4s" % "1.20.1",
      "com.typesafe.akka" %% "akka-http" % "10.1.0",
      "com.typesafe.akka" %% "akka-stream" % "2.5.11",
      "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.14.0",
      scalaTest % Test
    )
  )
  .dependsOn(infra)
  .dependsOn(infra % "test->test")
  .dependsOn(application)
  .dependsOn(application % "test->test")
  .dependsOn(domain)
  .dependsOn(domain % "test->test")
  .dependsOn(core)
  .dependsOn(core % "test->test")
  .aggregate(infra, application, domain, core)

lazy val infra = project
  .dependsOn(application)
  .dependsOn(application % "test->test")
  .dependsOn(domain)
  .dependsOn(domain % "test->test")
  .dependsOn(core)
  .dependsOn(core % "test->test")

lazy val application = project
  .dependsOn(domain)
  .dependsOn(domain % "test->test")
  .dependsOn(core)
  .dependsOn(core % "test->test")

lazy val domain = project
  .dependsOn(core)
  .dependsOn(core % "test->test")

lazy val core = project

enablePlugins(FlywayPlugin)
flywayUrl := "jdbc:mysql://localhost:3306/kanji_api"
flywayUser := "root"
flywayPassword := ""
flywayLocations += "db/migration"

flywayUrl in Test := "jdbc:mysql://localhost:3306/kanji_api_test"
flywayUser in Test := "root"
flywayPassword in Test := ""
flywayLocations in Test += "db/migration"
