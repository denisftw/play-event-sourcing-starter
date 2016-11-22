name := """play-event-sourcing-starter"""

val commonSettings = Seq(
  version := "1.0-SNAPSHOT",
  scalaVersion := "2.11.8",
  organization := "com.appliedscala.seeds.es"
)

lazy val root = (project in file(".")).
  settings(commonSettings).enablePlugins(PlayScala)

pipelineStages := Seq(digest)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  evolutions,
  "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided",
  "com.softwaremill.macwire" %% "util" % "2.2.5",
  "org.postgresql" % "postgresql" % "9.4.1207.jre7",
  "org.scalikejdbc" %% "scalikejdbc"       % "2.4.2",
  "org.scalikejdbc" %% "scalikejdbc-config"  % "2.4.2",
  "ch.qos.logback"  %  "logback-classic"   % "1.1.7",
  "de.svenkubiak" % "jBCrypt" % "0.4.1"
)

routesGenerator := InjectedRoutesGenerator
