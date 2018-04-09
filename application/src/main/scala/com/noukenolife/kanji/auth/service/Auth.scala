package com.noukenolife.kanji.auth.service

import cats.data.EitherT
import com.noukenolife.kanji.auth.entity.Account
import com.noukenolife.kanji.auth.value.Credential
import com.noukenolife.kanji.support.{AppError, IOContext}

import scala.concurrent.{ExecutionContext, Future}

trait Auth {
  def authenticate(credential: Credential)
    (implicit ctx: IOContext, ec: ExecutionContext): EitherT[Future, AppError, String]
  def retrieve(token: String)(implicit ctx: IOContext, ec: ExecutionContext): EitherT[Future, AppError, Account]
}
