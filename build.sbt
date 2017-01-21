import sbt.Keys._

name := "Radios"

version := "1.0"

scalaVersion := "2.11.8"

lazy val versions = new {
  val finatra = "2.7.0"
  val guice = "4.0"
  val mockito = "1.9.5"
  val scalatest = "3.0.0"
  val scalacheck = "1.13.4"
  val specs2 = "2.3.12"
  val typesafeConfig = "1.3.1"
}

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-thrift" % versions.finatra,
  "com.typesafe" % "config" % versions.typesafeConfig,

  "com.twitter" %% "finatra-http" % versions.finatra % "test",
  "com.twitter" %% "inject-server" % versions.finatra % "test",
  "com.twitter" %% "inject-app" % versions.finatra % "test",
  "com.twitter" %% "inject-core" % versions.finatra % "test",
  "com.twitter" %% "inject-modules" % versions.finatra % "test",
  "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",
  "org.mockito" % "mockito-core" % versions.mockito % "test",
  "org.scalacheck" %% "scalacheck" % versions.scalacheck % "test",
  "org.scalatest" %% "scalatest" % versions.scalatest % "test",
  "org.specs2" %% "specs2" % versions.specs2 % "test"
)