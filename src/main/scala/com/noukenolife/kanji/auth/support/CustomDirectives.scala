package com.noukenolife.kanji.auth.support

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directive1, Directives}
import cats.data.EitherT
import com.noukenolife.kanji.auth.error.UnexpectedError
import com.noukenolife.kanji.support.{AppError, DefaultJsonSupport}

import scala.concurrent.Future
import scala.util.Success

trait CustomDirectives extends DefaultJsonSupport {
  self: Directives =>

  def action[R](act: EitherT[Future, AppError, R]): Directive1[R] = {
    onComplete(act.value).flatMap {
      case Success(Right(r)) => provide(r)
      case Success(Left(e)) => complete(e.code, e)
      case _ => complete(StatusCodes.InternalServerError, UnexpectedError())
    }
  }
}
