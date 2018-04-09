package com.noukenolife.kanji.auth.jwt.authenticator

import cats.data.EitherT
import cats.implicits._
import com.noukenolife.kanji.auth.entity.Account
import com.noukenolife.kanji.auth.repository.AccountRepository
import com.noukenolife.kanji.auth.value.{AccountId, Credential, Email, Password}
import com.noukenolife.kanji.error.CustomError
import com.noukenolife.kanji.support.IOContext
import com.noukenolife.kanji.support.error.DomainError
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global

class JWTAuthenticatorSpec extends WordSpec with Matchers with MockFactory {

  implicit val ctx: IOContext = mock[IOContext]
  val credential = Credential(Email("example@gmail.com"), Password("example"))
  val account = Account(AccountId(1l), Email("example@gmail.com"), Password("example"))

  "A JWTAuthenticator" must {
    "apply from config" in {
      implicit val accountRepository: AccountRepository = stub[AccountRepository]
      val actual = JWTAuthenticator()
      val expected = JWTAuthenticator(
        "secret",
        Some("com.noukenolife.kanji"),
        Some("AccessToken"),
        Some(Set("http://localhost:9000")),
        Some(3 days)
      )

      actual shouldEqual expected
    }

    "init & retrieve" in {
      implicit val accountRepository: AccountRepository = stub[AccountRepository]

      val authenticator = JWTAuthenticator(
        secretKey = "secret",
        duration = Some(10.minutes)
      )

      (accountRepository.resolveByCredential(_: Credential)(_: IOContext, _: ExecutionContext))
        .when(credential, *, *)
        .returning(EitherT[Future, DomainError, Account](Future(Right(account))))

      (accountRepository.resolve(_: AccountId)(_: IOContext, _: ExecutionContext))
        .when(account.id, *, *)
        .returning(EitherT[Future, DomainError, Account](Future(Right(account))))

      Await.result(authenticator.init(credential).flatMap { token =>
        authenticator.retrieve(token)
      }.value, Duration.Inf) shouldEqual Right(account)
    }
  }
}
