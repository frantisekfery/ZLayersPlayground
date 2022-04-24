ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "StructuringServices"
  )

val zhttpVersion = "2.0.0-RC7"
libraryDependencies ++= List(
  "dev.zio" %% "zio" % "1.0.4-2",
)


//ThisBuild / version := "0.1.0-SNAPSHOT"
//
//ThisBuild / scalaVersion := "3.1.2"
//
//lazy val root = (project in file("."))
//  .settings(
//    name := "StructuringServices"
//  )
//
//libraryDependencies += "dev.zio" %% "zio" % "2.0.0-M6-2"


