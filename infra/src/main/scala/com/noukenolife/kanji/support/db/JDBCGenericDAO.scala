package com.noukenolife.kanji.support.db
import cats.data.EitherT
import com.noukenolife.kanji.support.InfraError
import com.noukenolife.kanji.support.error.{DAOError, RecordNotFound}
import com.noukenolife.kanji.util.TryEither

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

trait JDBCGenericDAO[R <: Record] extends GenericDAO[R] with JDBCSupport {
  protected def mapper: CRUDMapper[R]

  override def resolve(id: Long)(implicit ctx: Ctx, ec: ExecutionContext): EitherT[Future, InfraError, R] = {
    withDBSession(ctx) { implicit s =>
      TryEither.toEither(Try(mapper.findById(id).get), RecordNotFound())
    }
  }

  override def store(record: R)(implicit ctx: Ctx, ec: ExecutionContext): EitherT[Future, InfraError, Int] = {
    withDBSession(ctx) { implicit s =>
      TryEither.toEither(Try {
        mapper.findById(record.id) match {
          case None => mapper.createWithAttributes(mapper.toNamedValues(record): _*); 1
          case _ => mapper.updateById(record.id).withAttributes(mapper.toNamedValues(record): _*)
        }
      }, DAOError())
    }
  }

  override def delete(id: Long)(implicit ctx: Ctx, ec: ExecutionContext): EitherT[Future, InfraError, Int] = {
    withDBSession(ctx) { implicit s =>
      TryEither.toEither(Try(mapper.deleteById(id)), DAOError())
    }
  }
}
