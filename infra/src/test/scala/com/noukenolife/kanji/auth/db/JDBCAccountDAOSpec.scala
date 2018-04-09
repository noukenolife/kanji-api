package com.noukenolife.kanji.auth.db

import cats.implicits._
import com.noukenolife.kanji.auth.db.dao.JDBCAccountDAO
import com.noukenolife.kanji.auth.db.record.AccountRecord
import com.noukenolife.kanji.support.db.{Connection, JDBCIOContext}
import org.scalatest.{Matchers, fixture}
import scalikejdbc._
import scalikejdbc.DBSession
import scalikejdbc.scalatest.AutoRollback

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

class JDBCAccountDAOSpec extends fixture.WordSpec with Matchers with Connection with AutoRollback {

  val dao = new JDBCAccountDAO

  override def fixture(implicit session: DBSession): Unit = {
    val id = 1
    sql"""INSERT INTO `accounts` (`id`, `email`, `password`) VALUE (1, 'example@gmail.com', 'password')""".execute().apply()
  }

  "an JDBCAccountDAO" must {
    "resolve by email and password" in { implicit s =>
      implicit val ctx: JDBCIOContext = JDBCIOContext(s)
      Await.result(dao.resolveByEmailPassword("example@gmail.com", "password").value, Duration.Inf) shouldEqual
        Right(AccountRecord(1l, "example@gmail.com", "password"))
    }
  }
}
