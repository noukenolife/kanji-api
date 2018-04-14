package com.noukenolife.kanji.support.db.jdbc

import cats.data.EitherT
import com.noukenolife.kanji.support.{IOContext, Transactor}
import javax.inject.Inject
import scalikejdbc.DB

import scala.concurrent.{ExecutionContext, Future}

class JDBCTransactorImpl @Inject() extends Transactor {

  override def runAsync[L, R](f: IOContext => EitherT[Future, L, R])
    (implicit ec: ExecutionContext): EitherT[Future, L, R] = EitherT {
    DB.futureLocalTx { s =>
      f(JDBCIOContext(s)).value
    }
  }
}
