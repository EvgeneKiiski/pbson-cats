package pbson.cats

import cats.data._
import org.mongodb.scala.bson.{BsonArray, BsonDocument, BsonInt32, BsonValue}
import org.scalatest.{EitherValues, Matchers, ParallelTestExecution, WordSpec}
import pbson._

import scala.collection.immutable.SortedSet

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

  "NonEmptyList" should {
    case class Test(a: NonEmptyList[Int])
    "decode empty" in {
      val v: BsonValue = BsonDocument()
      v.fromBson[Test].isLeft shouldEqual true
    }
    "decode seq" in {
      val v: BsonValue = BsonDocument("a" -> BsonArray(BsonInt32(3), BsonInt32(4)))
      v.fromBson[Test].right.value shouldEqual Test(NonEmptyList.fromListUnsafe(List(3, 4)))
    }
  }

  "NonEmptyVector" should {
    case class Test(a: NonEmptyVector[Int])
    "decode empty" in {
      val v: BsonValue = BsonDocument()
      v.fromBson[Test].isLeft shouldEqual true
    }
    "decode seq" in {
      val v: BsonValue = BsonDocument("a" -> BsonArray(BsonInt32(3), BsonInt32(4)))
      v.fromBson[Test].right.value shouldEqual Test(NonEmptyVector.fromVectorUnsafe(Vector(3, 4)))
    }
  }

  "NonEmptySet" should {
    case class Test(a: NonEmptySet[Int])
    "decode empty" in {
      val v: BsonValue = BsonDocument()
      v.fromBson[Test].isLeft shouldEqual true
    }
    "decode seq" in {
      val v: BsonValue = BsonDocument("a" -> BsonArray(BsonInt32(3), BsonInt32(4)))
      v.fromBson[Test].right.value shouldEqual Test(NonEmptySet.fromSetUnsafe(SortedSet(3, 4)))
    }
  }

}
