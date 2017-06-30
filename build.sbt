name := "greeter"

organization := "net.fosdal.example"

scalaVersion := "2.11.11"

enablePlugins(BuildInfoPlugin)

fork := true

libraryDependencies ++= Seq(
  "com.github.pureconfig"      %% "pureconfig"                  % "0.7.2",
  "com.typesafe.scala-logging" %% "scala-logging"               % "3.5.0",
  "io.dropwizard.metrics"      % "metrics-core"                 % "3.2.3",
  "io.dropwizard.metrics"      % "metrics-healthchecks"         % "3.2.3",
  "io.dropwizard.metrics"      % "metrics-jvm"                  % "3.2.3",
  "io.dropwizard.metrics"      % "metrics-log4j2"               % "3.2.3",
  "joda-time"                  % "joda-time"                    % "2.9.9",
  "org.apache.logging.log4j"   % "log4j-1.2-api"                % "2.8.2",
  "org.apache.logging.log4j"   % "log4j-api"                    % "2.8.2",
  "org.apache.logging.log4j"   % "log4j-core"                   % "2.8.2",
  "org.apache.logging.log4j"   % "log4j-slf4j-impl"             % "2.8.2",
  "org.coursera"               % "metrics-datadog"              % "1.1.13",
  "org.joda"                   % "joda-convert"                 % "1.8.2",
  "org.scalacheck"             %% "scalacheck"                  % "1.13.5" % Test,
  "org.scalamock"              %% "scalamock-scalatest-support" % "3.6.0" % Test,
  "org.scalatest"              %% "scalatest"                   % "3.0.3" % Test,
  "org.slf4j"                  % "slf4j-api"                    % "1.7.25"
)

//
// Plugin Settings: sbt-assembly
//
mainClass in assembly := Some("net.fosdal.example.greeter.Main")

test in assembly := {}

assemblyMergeStrategy in assembly := {
  case "reference.conf" => MergeStrategy.concat
  case PathList("META-INF", xs @ _ *) =>
    xs.map(_.toLowerCase) match {
      case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
        MergeStrategy.discard
      case ps @ (e :: es) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
        MergeStrategy.discard
      case _ => MergeStrategy.discard
    }
  case _ => MergeStrategy.first
}

//
// BuildInfo Plugin Settings
//
buildInfoPackage := "net.fosdal.example.greeter"

buildInfoKeys := Seq[BuildInfoKey](
  organization,
  name,
  version,
  scalaVersion,
  sbtVersion,
  description,
  isSnapshot,
  git.gitHeadCommit,
  git.gitCurrentBranch,
  git.gitCurrentTags,
  git.gitUncommittedChanges,
  git.formattedShaVersion,
  git.formattedDateVersion,
  resolvers,
  libraryDependencies,
  scalacOptions in (Compile, compile),
  BuildInfoKey.action("appName")(name.value),
  BuildInfoKey.action("packageName")(organization.value),
  BuildInfoKey.action("configBase")("net.fosdal.example.greeter")
)

buildInfoOptions ++= Seq(BuildInfoOption.BuildTime, BuildInfoOption.ToJson, BuildInfoOption.ToMap)

//
// Plugin Settings: sbt-updates
//
dependencyUpdatesFilter -= moduleFilter(organization = "org.scala-lang")

//
// Plugin Settings: scalastyle-sbt-plugin
//
scalastyleFailOnError := true

(scalastyleConfig in Test) := baseDirectory.value / "scalastyle-test-config.xml"

//
// Plugin Settings: sbt-scoverage
//
coverageMinimum := 0

coverageFailOnMinimum := true

coverageEnabled := true

//
// Compiler Settings
//
// format: off
scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xfuture",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  "-Ywarn-value-discard"
)