package pbson.cats

import cats.data._
import cats.implicits._
import org.mongodb.scala.bson.BsonArray
import org.scalatest.{EitherValues, Matchers, ParallelTestExecution, WordSpec}
import pbson._

import scala.collection.immutable.SortedSet
import scala.collection.parallel.immutable


/**
  * @author Evgenii Kiiski 
  */
class CatsEncoderTest extends WordSpec with ParallelTestExecution with Matchers with EitherValues {

  "Chain" should {
    case class ChainTest(a: Chain[Int])
    "encode Empty" in {
      val test: ChainTest = ChainTest(Chain.empty)
      val bson = test.toBson
      bson.asDocument().containsKey("a") shouldEqual true
    }
    "encode Singleton" in {
      val test: ChainTest = ChainTest(Chain.one(3))
      val bson = test.toBson
      bson.asDocument().get("a") shouldEqual BsonArray(3)
    }
    "encode Append" in {
      val test: ChainTest = ChainTest(Chain.concat(Chain.one(3),Chain.one(4)))
      val bson = test.toBson
      bson.asDocument().get("a") shouldEqual BsonArray(3, 4)
    }
    "encode Wrap" in {
      val test: ChainTest = ChainTest(Chain.fromSeq(Seq(3, 4)))
      val bson = test.toBson
      bson.asDocument().get("a") shouldEqual BsonArray(3, 4)
    }
  }

  "NonEmptyChain" should {
    case class ChainTest(a: NonEmptyChain[Int])
    "encode Singleton" in {
      val test: ChainTest = ChainTest(NonEmptyChain.one(3))
      val bson = test.toBson
      bson.asDocument().get("a") shouldEqual BsonArray(3)
    }
    "encode Append" in {
      val test: ChainTest = ChainTest(NonEmptyChain.fromChainAppend(Chain.one(3),4))
      val bson = test.toBson
      bson.asDocument().get("a") shouldEqual BsonArray(3, 4)
    }
  }

  "NonEmptyList" should {
    case class Test(a: NonEmptyList[Int])
    "encode Singleton" in {
      val test: Test = Test(NonEmptyList.one(3))
      val bson = test.toBson
      bson.asDocument().get("a") shouldEqual BsonArray(3)
    }
    "encode List" in {
      val test: Test = Test(NonEmptyList.fromListUnsafe(List(3, 4)))
      val bson = test.toBson
      bson.asDocument().get("a") shouldEqual BsonArray(3, 4)
    }
  }

  "NonEmptyVector" should {
    case class Test(a: NonEmptyVector[Int])
    "encode Singleton" in {
      val test: Test = Test(NonEmptyVector.one(3))
      val bson = test.toBson
      bson.asDocument().get("a") shouldEqual BsonArray(3)
    }
    "encode List" in {
      val test: Test = Test(NonEmptyVector.fromVectorUnsafe(Vector(3, 4)))
      val bson = test.toBson
      bson.asDocument().get("a") shouldEqual BsonArray(3, 4)
    }
  }

  "NonEmptySet" should {
    case class Test(a: NonEmptySet[Int])
    "encode Singleton" in {
      val test: Test = Test(NonEmptySet.one(3))
      val bson = test.toBson
      bson.asDocument().get("a") shouldEqual BsonArray(3)
    }
    "encode List" in {
      val test: Test = Test(NonEmptySet.fromSetUnsafe(SortedSet(3, 4)))
      val bson = test.toBson
      bson.asDocument().get("a") shouldEqual BsonArray(3, 4)
    }
  }

}
