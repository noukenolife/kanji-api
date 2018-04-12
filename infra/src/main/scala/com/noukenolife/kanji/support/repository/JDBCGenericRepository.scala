package com.noukenolife.kanji.support.repository

import cats.data.EitherT
import cats.implicits._
import com.noukenolife.kanji.support.db.dao.JDBCGenericDAO
import com.noukenolife.kanji.support.db.jdbc.JDBCSupport
import com.noukenolife.kanji.support.db.record.Record
import com.noukenolife.kanji.support.error.{DomainError, EntityNotFound}
import com.noukenolife.kanji.support.{Entity, Id}

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions

trait JDBCGenericRepository[I <: Id[Long], E <: Entity[I], R <: Record] extends GenericRepository[I, E, R] with JDBCSupport {
  protected override def dao: JDBCGenericDAO[R]

  override def resolve(id: I)(implicit ctx: Ctx, ec: ExecutionContext): EitherT[Future, DomainError, E] = {
    dao.resolve(id.value)
      .map(toEntity)
      .leftMap(_ => EntityNotFound())
  }
  override def store(entity: E)(implicit ctx: Ctx, ec: ExecutionContext): EitherT[Future, DomainError, Unit] = ???
  override def remove(entity: E)(implicit ctx: Ctx, ec: ExecutionContext): EitherT[Future, DomainError, Unit] = ???
}
