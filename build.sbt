name := """akka-http-sample"""

organization := "ch.taggiasco"

version := "0.0.1"

scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaVersion = "2.4.8"

  Seq(
    "com.typesafe.akka" %% "akka-http-experimental"               % akkaVersion,
    "org.scalatest"     %% "scalatest"                            % "2.2.5" % "test",
    "com.typesafe.akka" %% "akka-testkit"                         % akkaVersion % "test"
  )
}
