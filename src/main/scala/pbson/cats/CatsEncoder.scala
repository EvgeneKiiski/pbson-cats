package pbson.cats

import cats.data._
import org.bson.{BsonArray, BsonDocument}
import pbson.{BsonEncoder, BsonMapEncoder}
import pbson.BsonEncoder.{BSON_NULL, BSON_UNDEFINED}

import scala.collection.JavaConverters._

/**
  * @author Evgenii Kiiski 
  */
trait CatsEncoder {

  implicit final def chainEncoder[A](implicit e: BsonEncoder[A]): BsonEncoder[Chain[A]] = t =>
    if(t.isEmpty) {
      BSON_NULL
    } else {
      new BsonArray(t.map(e.apply).toVector.asJava)
    }

  implicit final def nonEmptyChainEncoder[A](implicit e: BsonEncoder[A]): BsonEncoder[NonEmptyChain[A]] = t =>
      new BsonArray(t.toChain.map(e.apply).toVector.asJava)

  implicit final def nonEmptyListEncoder[A](implicit e: BsonEncoder[A]): BsonEncoder[NonEmptyList[A]] = t =>
    new BsonArray(t.map(e.apply).toList.asJava)

  implicit final def nonEmptyVectorEncoder[A](implicit e: BsonEncoder[A]): BsonEncoder[NonEmptyVector[A]] = t =>
    new BsonArray(t.map(e.apply).toVector.asJava)

  implicit final def nonEmptySetEncoder[A](implicit e: BsonEncoder[A]): BsonEncoder[NonEmptySet[A]] = t =>
    new BsonArray(t.toNonEmptyList.map(e.apply).toList.asJava)

  implicit final def nonEmptyMapEncoder[K, V](implicit
                                              e: BsonMapEncoder[K, V]
                                             ): BsonEncoder[NonEmptyMap[K, V]] = t =>
    {
      val doc = new BsonDocument()
      doc.putAll(t.toSortedMap.map(e.apply).asJava)
      doc
    }


}
