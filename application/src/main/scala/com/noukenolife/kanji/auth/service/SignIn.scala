package com.noukenolife.kanji.auth.service

import javax.inject.Inject

import cats.data.EitherT
import cats.implicits._
import com.noukenolife.kanji.auth.dto.SignInIO.{SignInInput, SignInOutput}
import com.noukenolife.kanji.auth.error.InvalidCredential
import com.noukenolife.kanji.auth.value.{Credential, Email, Password}
import com.noukenolife.kanji.support.{AppError, AppService, Transactor}

import scala.concurrent.{ExecutionContext, Future}

trait SignIn extends AppService[SignInInput, SignInOutput]

class SignInImpl @Inject() (
  auth: Auth,
  trans: Transactor
)(implicit override val ec: ExecutionContext) extends SignIn {

  override def invoke(input: SignInInput): EitherT[Future, AppError, SignInOutput] = trans.run { implicit ctx =>
    auth.authenticate(Credential(Email(input.email), Password(input.password)))
      .map(SignInOutput)
      .leftMap[AppError](_ => InvalidCredential())
  }
}
