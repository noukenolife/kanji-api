package com.noukenolife.kanji.support
import cats.data.EitherT

import scala.concurrent.{ExecutionContext, Future}

object FakeTransactor extends Transactor {
  override def run[A](f: IOContext => A): A = f(FakeIOContext())
  override def runAsync[A](f: IOContext => Future[A])(implicit ec: ExecutionContext): Future[A] = f(FakeIOContext())
  override def runAsync[E, A](f: IOContext => EitherT[Future, E, A])
    (implicit ec: ExecutionContext): EitherT[Future, E, A] = EitherT(f(FakeIOContext()).value)
}
