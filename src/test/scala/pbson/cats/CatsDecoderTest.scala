package pbson.cats

import cats.data.{Chain, NonEmptyChain}
import org.mongodb.scala.bson.{BsonArray, BsonDocument, BsonInt32, BsonString, BsonValue}
import org.scalatest.{EitherValues, Matchers, ParallelTestExecution, WordSpec}
import pbson._

/**
  * @author Evgenii Kiiski 
  */
class CatsDecoderTest extends WordSpec with ParallelTestExecution with Matchers with EitherValues {

  "Chain" should {
    case class ChainTest(a: Chain[Int])
    "decode empty" in {
      val v: BsonValue = BsonDocument()
      v.fromBson[ChainTest].right.value shouldEqual ChainTest(Chain.empty)
    }
    "decode seq" in {
      val v: BsonValue = BsonDocument("a" -> BsonArray(BsonInt32(3), BsonInt32(4)))
      v.fromBson[ChainTest].right.value shouldEqual ChainTest(Chain.fromSeq(Seq(3, 4)))
    }
  }

  "NonEmptyChain" should {
    case class ChainTest(a: NonEmptyChain[Int])
    "decode empty" in {
      val v: BsonValue = BsonDocument()
      v.fromBson[ChainTest].isLeft shouldEqual true
    }
    "decode seq" in {
      val v: BsonValue = BsonDocument("a" -> BsonArray(BsonInt32(3), BsonInt32(4)))
      v.fromBson[ChainTest].right.value shouldEqual ChainTest(NonEmptyChain.fromChainUnsafe(Chain.fromSeq(Seq(3, 4))))
    }
  }

}
