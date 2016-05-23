libraryDependencies += "org.scala-lang" %% "scala-pickling" % "0.8.0-SNAPSHOT"

lazy val root = (project in file(".")).
  settings(
    name := "mpjtest",
    version := "1.0",
    scalaVersion := "2.11.7"
  )

