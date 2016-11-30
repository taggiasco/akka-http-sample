name := """akka-http-sample"""

organization := "ch.taggiasco"

version := "0.0.2"

scalaVersion := "2.12.0"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaVersion     = "2.4.14"
  val akkaHttpVersion = "10.0.0"

  Seq(
    "com.typesafe.akka" %% "akka-http-core"     % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http"          % akkaHttpVersion,
    "org.scalatest"     %% "scalatest"          % "3.0.1"     % "test",
    "com.typesafe.akka" %% "akka-testkit"       % akkaVersion % "test"
  )
}
