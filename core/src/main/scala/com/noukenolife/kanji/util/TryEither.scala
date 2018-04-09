package com.noukenolife.kanji.util

import scala.util.{Success, Try}

object TryEither {
  def toEither[A, B](t: Try[A], orElse: B): Either[B, A] = {
    t match {
      case Success(r) => Right(r)
      case _ => Left(orElse)
    }
  }
}
