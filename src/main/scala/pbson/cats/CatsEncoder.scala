package pbson.cats

import cats.data.{Chain, NonEmptyChain}
import org.bson.BsonArray
import pbson.BsonEncoder
import pbson.BsonEncoder.BSON_NULL

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


}
