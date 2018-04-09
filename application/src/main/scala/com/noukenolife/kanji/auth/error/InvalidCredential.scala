package com.noukenolife.kanji.auth.error

import akka.http.scaladsl.model.StatusCodes
import com.noukenolife.kanji.support.AppError

case class InvalidCredential(
  code: Int = StatusCodes.Unauthorized.intValue,
  message: String = "Invalid credentials."
) extends AppError
