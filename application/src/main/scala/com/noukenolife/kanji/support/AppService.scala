package com.noukenolife.kanji.support

import cats.data.EitherT
import com.noukenolife.kanji.error.CustomError

import scala.concurrent.{ExecutionContext, Future}

trait AppService[I <: Input, O <: Output] {
  implicit def ec: ExecutionContext
  def invoke(input: I): EitherT[Future, AppError, O]
}
