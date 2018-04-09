package com.noukenolife.kanji.support.repository

import cats.data.EitherT
import com.noukenolife.kanji.support.db.JDBCGenericDAO
import com.noukenolife.kanji.support.entity.FakeEntity
import com.noukenolife.kanji.support.error.{EntityNotFound, RecordNotFound}
import com.noukenolife.kanji.support.record.FakeRecord
import com.noukenolife.kanji.support.value.FakeId
import com.noukenolife.kanji.support.{FakeIOContext, IOContext, InfraError}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.language.implicitConversions

class JDBCGenericRepositorySpec extends WordSpec with Matchers with MockFactory {

  val daoStub: JDBCGenericDAO[FakeRecord] = stub[JDBCGenericDAO[FakeRecord]]
  val repo: JDBCGenericRepository[FakeId, FakeEntity, FakeRecord] = new JDBCGenericRepository[FakeId, FakeEntity, FakeRecord] {
    protected override def dao: JDBCGenericDAO[FakeRecord] = daoStub
    override protected implicit def toEntity(record: FakeRecord): FakeEntity = {
      FakeEntity(FakeId(record.id), record.value)
    }
    override protected implicit def toRecord(entity: FakeEntity): FakeRecord = {
      FakeRecord(entity.id.value, entity.value)
    }
  }

  "A JDBCGenericRepository" must {
    "resolve" in {
      implicit val fakeIOContext: IOContext = FakeIOContext()
      val record = FakeRecord(1l, "This is a fake.")
      val entity = FakeEntity(FakeId(1l), "This is a fake.")

      (daoStub.resolve(_: Long)(_: IOContext, _: ExecutionContext))
        .when(1l, fakeIOContext, *)
        .returning(EitherT[Future, InfraError, FakeRecord](Future(Right(record))))

      Await.result(repo.resolve(FakeId(1l)).value, Duration.Inf) shouldEqual Right(entity)
    }
    "fail to resolve" in {
      implicit val fakeIOContext: IOContext = FakeIOContext()

      (daoStub.resolve(_: Long)(_: IOContext, _: ExecutionContext))
        .when(1l, fakeIOContext, *)
        .returning(EitherT[Future, InfraError, FakeRecord](Future(Left(RecordNotFound()))))

      Await.result(repo.resolve(FakeId(1l)).value, Duration.Inf) shouldEqual Left(EntityNotFound())
    }
  }
}
