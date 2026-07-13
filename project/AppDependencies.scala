import AppDependencies.playVersion
import sbt.Keys.libraryDependencies
import sbt.*

object AppDependencies {

  private val bootstrapVersion = "10.7.0"
  private val hmrcMongoVersion = "2.12.0"
  private val playVersion = "play-30"

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"             %% s"bootstrap-frontend-$playVersion"   % bootstrapVersion,
    "uk.gov.hmrc"             %% s"play-frontend-hmrc-$playVersion"   % "13.2.0",
    "uk.gov.hmrc.mongo"       %% s"hmrc-mongo-$playVersion"           % hmrcMongoVersion,
    "uk.gov.hmrc"             %% s"internal-auth-client-$playVersion" % "4.3.0",
    "org.typelevel"           %% "cats-core"                          % "2.13.0"
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"             %% s"bootstrap-test-$playVersion"     % bootstrapVersion,
    "uk.gov.hmrc.mongo"       %% s"hmrc-mongo-test-$playVersion"    % hmrcMongoVersion,
    "org.jsoup"               %  "jsoup"                            % "1.22.1"        ,
  ).map( _ % Test)

  val it: Seq[Nothing] = Seq.empty
}
