package pbson.cats

import cats.Order
import cats.data._
import org.bson.{BsonArray, BsonDocument, BsonType}
import pbson.{BsonDecoder, BsonMapDecoder}
import pbson.BsonDecoder._
import pbson.BsonError.{UnexpectedType, UnexpectedValue}
import pbson.utils.TraversableUtils.{traverse2List, traverse2Map, traverse2Seq, traverse2Vector}

import scala.collection.immutable.{SortedMap, SortedSet}


/**
  * @author Evgenii Kiiski 
  */
trait CatsDecoder {

  implicit final def chainDecoder[A](implicit d: BsonDecoder[A]): BsonDecoder[Chain[A]] = { b =>
    if (b == null) {
      Right(Chain.empty)
    } else if (b.getBsonType == BsonType.ARRAY) {
      traverse2Seq(b.asInstanceOf[BsonArray].getValues)(d.apply).map(Chain.fromSeq)
    } else {
      Left(UnexpectedType(b, BsonType.ARRAY))
    }
  }

  implicit final def nonEmptyChainDecoder[A](implicit d: BsonDecoder[A]): BsonDecoderNotNull[NonEmptyChain[A]] = { b =>
    if (b.getBsonType == BsonType.ARRAY) {
      val bsonArray = b.asInstanceOf[BsonArray].getValues
      if (bsonArray.isEmpty) {
        Left(UnexpectedValue("chain is empty"))
      } else {
        traverse2Seq(bsonArray)(d.apply)
          .map(s => NonEmptyChain.fromChainUnsafe(Chain.fromSeq(s)))
      }
    } else {
      Left(UnexpectedType(b, BsonType.ARRAY))
    }
  }

  implicit final def nonEmptyListDecoder[A](implicit d: BsonDecoder[A]): BsonDecoderNotNull[NonEmptyList[A]] = { b =>
    if (b.getBsonType == BsonType.ARRAY) {
      val bsonArray = b.asInstanceOf[BsonArray].getValues
      if (bsonArray.isEmpty) {
        Left(UnexpectedValue("list is empty"))
      } else {
        traverse2List(bsonArray)(d.apply)
          .map(NonEmptyList.fromListUnsafe)
      }
    } else {
      Left(UnexpectedType(b, BsonType.ARRAY))
    }
  }

  implicit final def nonEmptyVectorDecoder[A](implicit d: BsonDecoder[A]): BsonDecoderNotNull[NonEmptyVector[A]] = { b =>
    if (b.getBsonType == BsonType.ARRAY) {
      val bsonArray = b.asInstanceOf[BsonArray].getValues
      if (bsonArray.isEmpty) {
        Left(UnexpectedValue("list is empty"))
      } else {
        traverse2Vector(bsonArray)(d.apply)
          .map(NonEmptyVector.fromVectorUnsafe)
      }
    } else {
      Left(UnexpectedType(b, BsonType.ARRAY))
    }
  }

  implicit final def nonEmptySetDecoder[A](implicit
                                           d: BsonDecoder[A],
                                           o: Ordering[A]
                                          ): BsonDecoderNotNull[NonEmptySet[A]] = { b =>
    if (b.getBsonType == BsonType.ARRAY) {
      val bsonArray = b.asInstanceOf[BsonArray].getValues
      if (bsonArray.isEmpty) {
        Left(UnexpectedValue("list is empty"))
      } else {
        traverse2Seq(bsonArray)(d.apply)
          .map(s => NonEmptySet.fromSetUnsafe[A](SortedSet(s.toList: _ *)))
      }
    } else {
      Left(UnexpectedType(b, BsonType.ARRAY))
    }
  }

  implicit final def nonEmptyMapDecoder[K, V](implicit
                                              d: BsonMapDecoder[K, V],
                                              o: Ordering[K],
                                              or: Order[K]
                                             ): BsonDecoderNotNull[NonEmptyMap[K, V]] = { b =>
    if (b.getBsonType == BsonType.DOCUMENT) {
      traverse2Map(b.asInstanceOf[BsonDocument])(d.apply)
        .map(s => NonEmptyMap.fromMapUnsafe(SortedMap(s.toList: _*)))
    } else {
      Left(UnexpectedType(b, BsonType.DOCUMENT))
    }
  }

}
