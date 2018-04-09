package com.noukenolife.kanji.support

import cats.data.EitherT

import scala.concurrent.{ExecutionContext, Future}

trait Transactor {
  def run[A](f: IOContext => A): A
  def runAsync[A](f: IOContext => Future[A])(implicit ec: ExecutionContext): Future[A]
  def runAsync[E, A](f: IOContext => EitherT[Future, E, A])(implicit ec: ExecutionContext): EitherT[Future, E, A]
}
