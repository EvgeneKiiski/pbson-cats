
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

logBuffered in Test := false

lazy val commonSettings = Seq(
  organization := "ru.twistedlogic",
  version := "0.0.1",
  scalaVersion := "2.12.8",
  scalacOptions ++= Seq(
    "-encoding", "utf8", 
    "-Xfatal-warnings",  
    "-deprecation",
    "-unchecked",
    "-Ypartial-unification",
    "-language:implicitConversions",
    "-language:higherKinds",
    "-language:existentials",
    "-language:postfixOps"
  ),
  resolvers += "JCenter" at "https://jcenter.bintray.com/"
)

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    name := "pbson-cats",
    libraryDependencies ++= Seq(
      "ru.twistedlogic" %% "pbson" % "0.0.10",
      //"com.chuusai" %% "shapeless" % "2.3.3",
      "org.typelevel" %% "cats-core" % "1.5.0",
      "org.mongodb.scala" %% "mongo-scala-bson" % "2.6.0" % Test,
      "junit" % "junit" % "4.12" % Test,
      "org.typelevel" %% "discipline" % "0.10.0" % Test,
      "org.scalactic" %% "scalactic" % "3.0.5"  % Test,
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    ),
    publishTo := Some(Resolver.file("file",  new File( "repository" )) )
  )


lazy val examples = (project in file("examples"))
  .dependsOn(root)
  .settings(
    commonSettings,
    name := "examples",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "1.5.0",
      "org.typelevel" %% "cats-macros" % "1.5.0",
      "org.typelevel" %% "cats-kernel" % "1.5.0",
      "junit" % "junit" % "4.12" % Test,
      "org.typelevel" %% "discipline" % "0.10.0" % Test,
      "org.scalactic" %% "scalactic" % "3.0.5"  % Test,
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    )
  )










