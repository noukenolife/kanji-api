package com.noukenolife.kanji.support.db

import cats.data.EitherT
import com.noukenolife.kanji.support.{IOContext, InfraError}

import scala.concurrent.{ExecutionContext, Future}

trait GenericDAO[R <: Record] {

  type Ctx = IOContext

  def resolve(id: Long)(implicit ctx: Ctx, ec: ExecutionContext): EitherT[Future, InfraError, R]
  def store(record: R)(implicit ctx: Ctx, ec: ExecutionContext): EitherT[Future, InfraError, Int]
  def delete(id: Long)(implicit ctx: Ctx, ec: ExecutionContext): EitherT[Future, InfraError, Int]
}
