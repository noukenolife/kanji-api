package com.noukenolife.kanji.auth.repository

import cats.data.EitherT
import cats.implicits._
import com.noukenolife.kanji.auth.db.dao.JDBCAccountDAO
import com.noukenolife.kanji.auth.db.record.AccountRecord
import com.noukenolife.kanji.auth.entity.Account
import com.noukenolife.kanji.auth.value.{AccountId, Credential, Email, Password}
import com.noukenolife.kanji.support.error.{DomainError, EntityNotFound}
import com.noukenolife.kanji.support.repository.JDBCGenericRepository

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions

class JDBCAccountRepository extends JDBCGenericRepository[AccountId, Account, AccountRecord] with AccountRepository {
  override protected implicit def toEntity(record: AccountRecord): Account = {
    Account(AccountId(record.id), Email(record.email), Password(record.password))
  }
  override protected implicit def toRecord(entity: Account): AccountRecord = {
    AccountRecord(entity.id.value, entity.email.value, entity.password.value)
  }
  override protected def dao: JDBCAccountDAO = new JDBCAccountDAO

  override def resolveByCredential(credential: Credential)
    (implicit ctx: Ctx, ec: ExecutionContext): EitherT[Future, DomainError, Account] = {
    dao.resolveByEmailPassword(credential.email.value, credential.password.value)
      .map(toEntity)
      .leftMap(_ => EntityNotFound())
  }
}
