import sbt._

object Versions {
  val Logback = "1.2.3"
  val Logstash = "6.3"
  val ScalaLogging = "3.9.2"
  val ScalaTest = "3.2.0-M4"
  val ScalaMock = "4.4.0"
  val Vertx = "4.0.0-milestone5"
}

object Dependencies {

  val Logging = Seq(
    "ch.qos.logback" % "logback-classic" % Versions.Logback,
    "com.typesafe.scala-logging" %% "scala-logging" % Versions.ScalaLogging,
    "net.logstash.logback" % "logstash-logback-encoder" % Versions.Logstash
  )

  val ScalaTest = "org.scalatest" %% "scalatest" % Versions.ScalaTest
  val ScalaMock = "org.scalamock" %% "scalamock" % Versions.ScalaMock

  val VertxCircuitBreaker = "io.vertx" % "vertx-circuit-breaker" % Versions.Vertx
  val VertxKafka = "io.vertx" % "vertx-kafka-client" % Versions.Vertx
  val VertxPrometheus = "io.vertx" % "vertx-micrometer-metrics" % Versions.Vertx
  val VertxRxJava2 = "io.vertx" % "vertx-rx-java2" % Versions.Vertx
  val VertxWeb = "io.vertx" % "vertx-web" % Versions.Vertx

  val GtfsApi: Seq[ModuleID] = Logging ++ Seq(
    VertxCircuitBreaker,
    VertxKafka,
    VertxPrometheus,
    VertxRxJava2,
    VertxWeb,
    ScalaTest % Test,
    ScalaMock % Test
  )
}

