package com.noukenolife.kanji.auth.db.dao

import cats.data.EitherT
import com.noukenolife.kanji.auth.db.record.AccountRecord
import com.noukenolife.kanji.support.InfraError
import com.noukenolife.kanji.support.db.{CRUDMapper, JDBCGenericDAO}
import com.noukenolife.kanji.support.error.{DAOError, RecordNotFound}
import scalikejdbc._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class JDBCAccountDAO extends JDBCGenericDAO[AccountRecord] {
  override protected def mapper: CRUDMapper[AccountRecord] = AccountRecord

  def resolveByEmailPassword(email: String, password: String)
    (implicit ctx: Ctx, ec: ExecutionContext): EitherT[Future, InfraError, AccountRecord] = {
    withDBSession(ctx) { implicit s =>
      Try {
        val a = mapper.defaultAlias
        mapper.findBy(sqls
          .eq(a.email, email)
          .and
          .eq(a.password, password)
        )
      }.recover{ case e: Throwable => println(e); throw e }.map {
        _.map(Right(_)).getOrElse(Left(RecordNotFound()))
      }
      .getOrElse(Left(DAOError()))
    }
  }
}
