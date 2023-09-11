ThisBuild / scalaVersion := "3.3.1"
ThisBuild / version := "0.0.1"
ThisBuild / organization := "com.stulsoft"
ThisBuild / organizationName := "stulsoft"

lazy val http4sVersion = "0.23.23"
lazy val circeVersion = "0.14.6"

lazy val root = (project in file("."))
  .settings(
    name := "ys-http4s",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.4.7",

    // http4s
    libraryDependencies += "org.http4s" %% "http4s-ember-client" % http4sVersion,
    libraryDependencies += "org.http4s" %% "http4s-ember-server" % http4sVersion,
    libraryDependencies += "org.http4s" %% "http4s-dsl" % http4sVersion,
    // circe
    libraryDependencies += "org.http4s" %% "http4s-circe" % http4sVersion,
    libraryDependencies += "io.circe" %% "circe-generic" % circeVersion,
    libraryDependencies += "io.circe" %% "circe-literal" % circeVersion,

    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % Test,

    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-unchecked",
      "-language:postfixOps",
      "-Xfatal-warnings"
    )
  )

//addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)