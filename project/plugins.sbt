credentials ++= {
  val alreadyContainsCredentials = credentials.value.collect {
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

resolvers ++= Seq(
  "Maven Public" at "https://nexus.unterwegs.io/repository/maven-public/"
)

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.10.0-RC1")
addSbtPlugin("com.eed3si9n"     % "sbt-assembly"         % "0.14.10")
addSbtPlugin("org.scalameta"    % "sbt-scalafmt"         % "2.4.0")

