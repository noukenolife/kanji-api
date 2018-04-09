package com.noukenolife.kanji.support

import scala.concurrent.{ExecutionContext, Future}

trait Transactor {
  def run[A](f: IOContext => A): A
  def run[A](f: IOContext => Future[A])(implicit ec: ExecutionContext): Future[A]
}
