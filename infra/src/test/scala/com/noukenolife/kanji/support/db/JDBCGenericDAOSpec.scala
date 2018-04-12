package com.noukenolife.kanji.support.db

import cats.implicits._
import com.noukenolife.kanji.support.IOContext
import com.noukenolife.kanji.support.db.dao.{CRUDMapper, JDBCGenericDAO}
import com.noukenolife.kanji.support.db.jdbc.JDBCIOContext
import com.noukenolife.kanji.support.error.RecordNotFound
import com.noukenolife.kanji.support.record.FakeRecord
import org.scalatest.{Matchers, fixture}
import scalikejdbc._
import scalikejdbc.scalatest.AutoRollback

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class JDBCGenericDAOSpec extends fixture.WordSpec with Matchers with Connection with AutoRollback {

  val dao = new JDBCGenericDAO[FakeRecord] {
    override def mapper: CRUDMapper[FakeRecord] = FakeRecord
  }
  val record = new FakeRecord(1l, "This is a fake")

  override def fixture(implicit session: DBSession): Unit = {
    sql"""
      CREATE TABLE IF NOT EXISTS `fakes` (
        `id` BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
        `value` VARCHAR(20) NOT NULL
      )
      """.execute().apply()
  }

  "A JDBCGenericDAO" must {
    "store & resolve & delete" in { implicit s =>
      implicit val ctx: IOContext = JDBCIOContext(s)

      // store a new record
      Await.result(dao.store(record).value, Duration.Inf) shouldEqual Right(1)
      Await.result(dao.resolve(1l).value, Duration.Inf) shouldEqual Right(record)

      // update a record
      Await.result(
        dao.store(record.copy(value = "This is a new fake"))
          .flatMap(_ => dao.resolve(1l)).value
      , Duration.Inf) shouldEqual Right(record.copy(value = "This is a new fake"))

      // delete a record
      Await.result(dao.delete(1l).value, Duration.Inf) shouldEqual Right(1)
      Await.result(dao.resolve(1l).value, Duration.Inf) shouldEqual Left(RecordNotFound())
    }
  }
}
