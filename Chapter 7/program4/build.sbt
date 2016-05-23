libraryDependencies += "org.scala-lang.modules" %% "scala-pickling" % "0.10.1"

lazy val root = (project in file(".")).
  settings(
    name := "mpjtest",
    version := "1.0",
    scalaVersion := "2.11.7"
  )

