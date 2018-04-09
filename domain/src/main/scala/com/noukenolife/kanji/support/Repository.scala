package com.noukenolife.kanji.support

import cats.data.EitherT
import com.noukenolife.kanji.support.error.DomainError

import scala.concurrent.{ExecutionContext, Future}

trait Repository[I <: Id[_], E <: Entity[I]] {

  type Ctx = IOContext

  def resolve(id: I)(implicit ctx: Ctx, ec: ExecutionContext): EitherT[Future, DomainError, E]
  def store(entity: E)(implicit ctx: Ctx, ec: ExecutionContext): EitherT[Future, DomainError, Unit]
  def remove(entity: E)(implicit ctx: Ctx, ec: ExecutionContext): EitherT[Future, DomainError, Unit]
}
