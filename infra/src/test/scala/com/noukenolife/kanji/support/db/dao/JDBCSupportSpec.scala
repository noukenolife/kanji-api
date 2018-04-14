package com.noukenolife.kanji.support.db.dao

import com.noukenolife.kanji.support.FakeIOContext
import com.noukenolife.kanji.support.db.Connection
import com.noukenolife.kanji.support.db.jdbc.{JDBCIOContext, JDBCSupport}
import com.noukenolife.kanji.support.error.InvalidIOContext
import org.scalatest.{Matchers, WordSpec}
import scalikejdbc._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class JDBCSupportSpec extends WordSpec with Matchers with JDBCSupport with Connection {

  "A JDBCSupport" must {
    "run withSession" in {
      Await.result(withDBSession(JDBCIOContext()) { implicit s =>
          Right(sql"""SELECT 1""".execute().apply())
      }.value, Duration.Inf) shouldEqual Right(true)
    }
    "fail to run withSession if JDBCIOContext is not passed" in {
      Await.result(withDBSession(FakeIOContext()) { implicit s =>
        Right(sql"""SELECT 1""".execute().apply())
      }.value, Duration.Inf) shouldEqual Left(InvalidIOContext())
    }
  }
}
