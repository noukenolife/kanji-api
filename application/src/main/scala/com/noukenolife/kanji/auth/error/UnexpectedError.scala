package com.noukenolife.kanji.auth.error

import akka.http.scaladsl.model.StatusCodes
import com.noukenolife.kanji.support.AppError

case class UnexpectedError(
  code: Int = StatusCodes.InternalServerError.intValue,
  message: String = "An unexpected error occurred."
) extends AppError
