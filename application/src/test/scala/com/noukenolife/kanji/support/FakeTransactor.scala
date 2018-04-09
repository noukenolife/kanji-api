package com.noukenolife.kanji.support
import scala.concurrent.{ExecutionContext, Future}

object FakeTransactor extends Transactor {
  override def run[A](f: IOContext => A): A = f(FakeIOContext())
  override def run[A](f: IOContext => Future[A])(implicit ec: ExecutionContext): Future[A] = f(FakeIOContext())
}
