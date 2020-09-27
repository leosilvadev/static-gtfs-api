import Build._

lazy val staticGtfsApi = project
  .in(file("static-gtfs-api"))
  .settings(
    name := "static-gtfs-api",
    CommonSettings,
    AssemblySettings("com.github.leosilvadev.gtfs.StaticGtfsApp"),
    libraryDependencies ++= Dependencies.GtfsApi
  )

lazy val root = project
  .in(file("."))
  .aggregate(
    staticGtfsApi
  )
  .settings(
    name := "gtfs",
    NonPublishableSettings
  )

