import sbt.Keys._

name := "Radios"

version := "1.0"

scalaVersion := "2.11.8"

lazy val versions = new {
  val finatra = "2.7.0"
  val finagle = "6.41.0"
  val finagle_auth = "0.1.0"
  val swagger_finatra = "0.7.1"
  val guice = "4.0"
  val mockito = "1.9.5"
  val scalatest = "3.0.0"
  val scalacheck = "1.13.4"
  val specs2 = "2.3.12"
  val typesafeConfig = "1.3.1"
}

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-thrift" % versions.finatra,
  "com.github.finagle" %% "finagle-http-auth" % versions.finagle_auth,
  "com.typesafe" % "config" % versions.typesafeConfig,
  "com.github.xiaodongw" %% "swagger-finatra" % versions.swagger_finatra,

  "com.twitter" %% "finatra-http" % versions.finatra % "test",
  "com.twitter" %% "inject-server" % versions.finatra % "test",
  "com.twitter" %% "inject-app" % versions.finatra % "test",
  "com.twitter" %% "inject-core" % versions.finatra % "test",
  "com.twitter" %% "inject-modules" % versions.finatra % "test",
  "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",


  "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests",

  "org.mockito" % "mockito-core" % versions.mockito % "test",
  "org.scalacheck" %% "scalacheck" % versions.scalacheck % "test",
  "org.scalatest" %% "scalatest" % versions.scalatest % "test",
  "org.specs2" %% "specs2" % versions.specs2 % "test"
)