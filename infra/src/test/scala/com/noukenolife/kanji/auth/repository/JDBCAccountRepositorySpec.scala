package com.noukenolife.kanji.auth.repository

import cats.data.EitherT
import com.noukenolife.kanji.auth.db.dao.JDBCAccountDAO
import com.noukenolife.kanji.auth.db.record.AccountRecord
import com.noukenolife.kanji.auth.entity.Account
import com.noukenolife.kanji.auth.value.{AccountId, Credential, Email, Password}
import com.noukenolife.kanji.support.error.{EntityNotFound, RecordNotFound}
import com.noukenolife.kanji.support.{FakeIOContext, IOContext, InfraError}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class JDBCAccountRepositorySpec extends WordSpec with Matchers with MockFactory {

  val daoStub: JDBCAccountDAO = stub[JDBCAccountDAO]
  val repo: JDBCAccountRepository = new JDBCAccountRepository {
    override protected def dao: JDBCAccountDAO = daoStub
  }

  "A JDBCAccountRepository" must {
    "resolveByCredential" in {
      implicit val ctx: FakeIOContext = FakeIOContext()

      val credential = Credential(Email("example@gmail.com"), Password("example"))

      (daoStub.resolveByEmailPassword(_: String, _: String)(_: IOContext, _: ExecutionContext))
        .when("example@gmail.com", "example", ctx, *)
        .returning(
          EitherT[Future, InfraError, AccountRecord](Future(Right(AccountRecord(1l, "example@gmail.com", "example"))))
        )

      Await.result(repo.resolveByCredential(credential).value, Duration.Inf) shouldEqual Right(
        Account(AccountId(1l), Email("example@gmail.com"), Password("example"))
      )
    }
    "fail to resolveByCredential" in {
      implicit val ctx: FakeIOContext = FakeIOContext()

      val credential = Credential(Email("invalid@gmail.com"), Password("invalid"))

      (daoStub.resolveByEmailPassword(_: String, _: String)(_: IOContext, _: ExecutionContext))
        .when("invalid@gmail.com", "invalid", ctx, *)
        .returning(EitherT[Future, InfraError, AccountRecord](Future(Left(RecordNotFound()))))

      Await.result(repo.resolveByCredential(credential).value, Duration.Inf) shouldEqual Left(EntityNotFound())
    }
  }
}
