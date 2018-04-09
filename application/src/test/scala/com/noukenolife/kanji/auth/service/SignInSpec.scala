package com.noukenolife.kanji.auth.service

import cats.data.EitherT
import com.noukenolife.kanji.auth.dto.SignInIO.{SignInInput, SignInOutput}
import com.noukenolife.kanji.auth.value.Credential
import com.noukenolife.kanji.support.{AppError, FakeTransactor, IOContext}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class SignInSpec extends WordSpec with Matchers with MockFactory {

  "A SignIn" must {
    "invoke" in {
      val input = SignInInput("example@gmail.com", "example")
      val output = SignInOutput("token")
      val credential = input.credential
      val auth = stub[Auth]

      (auth.authenticate(_: Credential)(_: IOContext, _: ExecutionContext))
        .when(credential, *, *)
        .returning(EitherT[Future, AppError, String](Future(Right(output.token))))

      val service = new SignInImpl(auth, FakeTransactor)
      Await.result(service.invoke(input).value, Duration.Inf) shouldEqual Right(output)
    }
  }
}
