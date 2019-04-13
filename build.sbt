name := """eventos"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "2.23.0"
libraryDependencies += "joda-time" % "joda-time" % "2.10.1"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "3.0.1"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "3.0.1"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.13"
libraryDependencies +=  "com.pauldijou" %% "jwt-play-json" % "2.1.0"
