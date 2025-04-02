lazy val baseName = "scala-multi-page-forms"

version := "0.1"

val scala3Version = "3.4.2"
val scala213Version = "2.13.16"
// val scala212Version = "2.12.17"
lazy val supportedScalaVersions = List(scala3Version, scala213Version)

scalaVersion := scala213Version

val formatAndTest =
  taskKey[Unit](
    "format all code then run tests, do not use on CI as any changes will not be committed"
  )

lazy val commonSettings = Seq(
  scalaVersion := scala213Version,
  crossScalaVersions := supportedScalaVersions,
  scalacOptions ++= Seq(
    "-encoding",
    "utf8",
    "-feature",
    "-language:implicitConversions",
    "-language:existentials",
    "-unchecked"
  ) ++
    (CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, 13)) => Seq("-Ytasty-reader") // flags only needed in Scala 2
      case Some((3, _)) => Seq("-no-indent") // flags only needed in Scala 3
      case _ => Seq.empty
    }),
  formatAndTest := {
    (Test / test)
      .dependsOn(Compile / scalafmtAll)
      .dependsOn(Test / scalafmtAll)
  }.value,
  Test / test := (Test / test)
    .dependsOn(Compile / scalafmtCheck)
    .dependsOn(Test / scalafmtCheck)
    .value
)

val circeVersion = "0.14.12"
lazy val scalaMultiPageForms = (project in file("."))
  .settings(
    commonSettings,
    libraryDependencies ++= Vector(
      "com.chuusai" %% "shapeless" % "2.3.3",
      "org.typelevel" %% "cats-core" % "2.13.0",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "org.scalatest" %% "scalatest" % "3.2.13" % Test
    ),
    publish / skip := true
  )
