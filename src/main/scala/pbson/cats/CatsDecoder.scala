package pbson.cats

import cats.data.{Chain, NonEmptyChain}
import cats.data.Chain.Wrap
import org.bson.{BsonArray, BsonType}
import pbson.BsonDecoder
import pbson.BsonDecoder._
import pbson.BsonError.{UnexpectedType, UnexpectedValue}
import pbson.utils.TraversableUtils.traverse2Seq


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

}
