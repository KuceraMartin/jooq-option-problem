lazy val root = project
  .in(file("."))
  .aggregate(jooqGenerated, app)
  .settings(
    publish / skip := true
  )

lazy val jooqGenerated = project
  .in(file("jooq-generated"))
  .settings(
    name := "jooq-generated",
    scalaVersion := "2.13.15",
    publish / skip := true,
    Test / mainClass := Some("GenerateJooqCode"),
    libraryDependencies ++= Vector(
      "org.jooq" % "jooq" % "3.19.15",
      "org.jooq" % "jooq-codegen" % "3.19.15" % Test,
      "ch.qos.logback" % "logback-classic" % "1.5.11" % Test,
      "org.postgresql" % "postgresql" % "42.7.4" % Test,
      "com.dimafeng" %% "testcontainers-scala-core" % "0.41.4" % Test
    )
  )

lazy val app = project
  .in(file("app"))
  .dependsOn(jooqGenerated)
  .settings(
    name := "app",
    scalaVersion := "2.13.15",
    publish / skip := true,
    libraryDependencies ++= Vector(
      "org.postgresql" % "postgresql" % "42.7.4"
    )
  )

addCommandAlias("generateJooq", "jooqGenerated / Test / run")
