# pbson-cats

It is a scala library based on [PBson](https://evgenekiiski.github.io/pbson/). 
It contains BSON encoder and decoder for [cats](https://typelevel.org/cats/) types.

Supports types: Chain, NonEmptyChain, NonEmptyList, NonEmptyVector, NonEmptyMap

## Getting pbson-cats

The current stable version is 0.0.1

If you're using SBT, add the following line to your build file:

```scala
resolvers += "JCenter" at "https://jcenter.bintray.com/"
libraryDependencies += "ru.twistedlogic" %% "pbson-cats" % "0.0.1"
```