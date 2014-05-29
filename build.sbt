name := """rest-helper"""

organization := "org.ababup1192"

version := "0.0.2"

scalaVersion := "2.10.2"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
	"play" % "play_2.10" % "2.1.3",
	"org.specs2" %% "specs2" % "2.3.12" % "test"
)