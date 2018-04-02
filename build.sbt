name := "Scala_Currency"

version := "0.1"

scalaVersion := "2.11.7"

val specs2Version = "3.6.2"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % specs2Version withSources(),
  "org.specs2" %% "specs2-mock" % specs2Version withSources(),
  "org.specs2" %% "specs2-matcher-extra" % specs2Version withSources(),
  "org.specs2" %% "specs2-junit" % specs2Version withSources()
)