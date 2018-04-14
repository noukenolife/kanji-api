import Dependencies.scalaTest

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.1.0",
  "com.pauldijou" %% "jwt-json4s-native" % "0.16.0",
  "com.iheart" %% "ficus" % "1.4.3",
  "com.google.inject" % "guice" % "4.0",
  "net.codingwell" %% "scala-guice" % "4.1.1",
  "mysql" % "mysql-connector-java" % "6.0.6",
  "org.scalikejdbc" %% "scalikejdbc" % "3.1.0",
  "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % "3.1.0",
  "org.skinny-framework" %% "skinny-orm" % "2.5.2",
  "com.zaxxer" % "HikariCP" % "3.1.0"
)

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc-test" % "3.2.2" % Test,
  "org.scalamock" %% "scalamock" % "4.1.0" % Test,
  scalaTest % Test
)
