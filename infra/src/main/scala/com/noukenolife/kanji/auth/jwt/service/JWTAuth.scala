package com.noukenolife.kanji.auth.jwt.service

import javax.inject.Inject

import cats.data.EitherT
import cats.implicits._
import com.noukenolife.kanji.auth.entity.Account
import com.noukenolife.kanji.auth.error.InvalidCredential
import com.noukenolife.kanji.auth.jwt.authenticator.JWTAuthenticator
import com.noukenolife.kanji.auth.repository.AccountRepository
import com.noukenolife.kanji.auth.service.Auth
import com.noukenolife.kanji.auth.value.Credential
import com.noukenolife.kanji.support.{AppError, IOContext}

import scala.concurrent.{ExecutionContext, Future}

class JWTAuth @Inject()(implicit accountRepository: AccountRepository) extends Auth {
  override def authenticate(credential: Credential)
    (implicit ctx: IOContext, ec: ExecutionContext): EitherT[Future, AppError, String] = {
    JWTAuthenticator().init(credential).leftMap(_ => InvalidCredential())
  }
  override def retrieve(token: String)
    (implicit ctx: IOContext, ec: ExecutionContext): EitherT[Future, AppError, Account] = {
    JWTAuthenticator().retrieve(token).leftMap(_ => InvalidCredential())
  }
}
