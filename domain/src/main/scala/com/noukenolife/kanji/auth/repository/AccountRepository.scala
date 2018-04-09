package com.noukenolife.kanji.auth.repository

import cats.data.EitherT
import com.noukenolife.kanji.auth.entity.Account
import com.noukenolife.kanji.auth.value.{AccountId, Credential}
import com.noukenolife.kanji.support.Repository
import com.noukenolife.kanji.support.error.DomainError

import scala.concurrent.{ExecutionContext, Future}

trait AccountRepository extends Repository[AccountId, Account] {
  def resolveByCredential(credential: Credential)(implicit ctx: Ctx, ec: ExecutionContext):  EitherT[Future, DomainError, Account]
}
