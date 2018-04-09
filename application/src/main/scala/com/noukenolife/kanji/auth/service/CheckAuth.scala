package com.noukenolife.kanji.auth.service

import javax.inject.Inject

import cats.data.EitherT
import cats.implicits._
import com.noukenolife.kanji.auth.dto.CheckAuthIO.{CheckAuthInput, CheckAuthOutput}
import com.noukenolife.kanji.auth.error.InvalidCredential
import com.noukenolife.kanji.support.{AppError, AppService, Transactor}

import scala.concurrent.{ExecutionContext, Future}

trait CheckAuth extends AppService[CheckAuthInput, CheckAuthOutput]

class CheckAuthImpl @Inject()(
  auth: Auth,
  trans: Transactor
)(implicit override val ec: ExecutionContext) extends CheckAuth {

  override def invoke(input: CheckAuthInput): EitherT[Future, AppError, CheckAuthOutput] = trans.run { implicit ctx =>
    auth.retrieve(input.token)
      .map(CheckAuthOutput)
      .leftMap(_ => InvalidCredential())
  }
}
