package com.noukenolife.kanji.util

import org.scalatest.{Matchers, WordSpec}

import scala.util.Try

class TryEitherSpec extends WordSpec with Matchers {

  "A TryEither" must {
    "toEither" in {
      val error = new Error()

      TryEither.toEither(Try(), error) shouldEqual Right((): Unit)
      TryEither.toEither(Try(throw new Exception()), error) shouldEqual Left(error)
    }
  }
}
