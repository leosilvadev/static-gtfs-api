import org.scalafmt.sbt.ScalafmtPlugin.autoImport.scalafmtOnCompile
import sbt.Keys._
import sbt._
import sbtassembly.AssemblyPlugin.autoImport._

object Build {

  private object ProjectResolvers {

    val MavenPublic = "Maven Public" at "https://nexus.unterwegs.io/repository/maven-public/"
    val Confluent = "Confluent" at "https://packages.confluent.io/maven/"

    val AllResolvers = Seq(MavenPublic, Confluent)

    def mobimeoCredentials(credentials: Seq[Credentials]): Seq[Credentials] = {
      val alreadyContainsCredentials = credentials.collect {
        case d: DirectCredentials => d.host == "nexus.unterwegs.io"
      }.nonEmpty
      if (!alreadyContainsCredentials) {
        val env = sys.env.get(_)
        (for {
          user     <- env("PUBLIC_NEXUS_USERNAME")
          password <- env("PUBLIC_NEXUS_PASSWORD")
        } yield Credentials(
          "Sonatype Nexus Repository Manager",
          "nexus.unterwegs.io",
          user,
          password
        )).toSeq
      } else Seq.empty
    }
  }

  val CommonSettings: Seq[Def.Setting[_]] = Seq(
    organization := "com.mobimeo.data",
    scalaVersion := "2.13.1",
    resolvers ++= ProjectResolvers.AllResolvers,
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding",
      "UTF-8",
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-unchecked",
      "-Werror",
      "-Xlint",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard",
      "-Wunused"
    ),
    fork in run := true,
    logBuffered in Test := false,
    scalafmtOnCompile := !sys.env.keySet.contains("CI_SERVER")
  )

  val NonPublishableSettings: Seq[Def.Setting[_]] = Seq(
    publish := {},
    publishLocal := {},
    test := {},
    testOnly := {},
    publishArtifact := false
  )

  val ItSettings: Seq[Def.Setting[_]] = Defaults.itSettings ++ Seq(
    logBuffered in IntegrationTest := false
  )

  def AssemblySettings(main: String): Seq[Def.Setting[_]] = Seq(
    assemblyJarName in assembly := "app.jar",
    test in assembly := {},
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", _ @_*) => MergeStrategy.discard
      case _                           => MergeStrategy.first
    },
    mainClass in assembly := Some(main)
  )
}
