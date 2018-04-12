package com.noukenolife.kanji.support.db.jdbc

import cats.data._
import cats.implicits._
import com.noukenolife.kanji.support.error.InvalidIOContext
import com.noukenolife.kanji.support.{IOContext, InfraError}
import scalikejdbc.DBSession

import scala.concurrent.{ExecutionContext, Future, blocking}

trait JDBCSupport {
  def withDBSession[A](ctx: IOContext)(f: DBSession => Either[InfraError, A])
    (implicit ec: ExecutionContext): EitherT[Future, InfraError, A] = ctx match {
    case JDBCIOContext(dbSession) => EitherT(Future {
      blocking(f(dbSession))
    })
    case _ => EitherT.left[A](Future.successful(InvalidIOContext()))
  }
}
