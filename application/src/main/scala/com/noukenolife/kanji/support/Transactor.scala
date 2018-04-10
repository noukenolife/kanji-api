package com.noukenolife.kanji.support

import cats.data.EitherT

import scala.concurrent.{ExecutionContext, Future}

trait Transactor {
  def runAsync[E, A](f: IOContext => EitherT[Future, E, A])(implicit ec: ExecutionContext): EitherT[Future, E, A]
}
