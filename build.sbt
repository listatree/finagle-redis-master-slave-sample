name := "finagle-redis-master-slave-sample"

scalaVersion  := "2.11.6"

resolvers ++= Seq(
  "Twitter repository" at "http://maven.twttr.com"
)

libraryDependencies ++= {
  Seq(
    "com.twitter"             %% "finagle-redis"    % "6.25.0"      withSources(),
    "org.specs2"              %% "specs2"           % "2.4.2"       % "test"          withSources()
  )
}
