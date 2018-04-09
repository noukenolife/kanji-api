package com.noukenolife.kanji.auth.support

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directive1, Directives}
import com.noukenolife.kanji.auth.dto.CheckAuthIO.CheckAuthInput
import com.noukenolife.kanji.auth.entity.Account
import com.noukenolife.kanji.auth.service.CheckAuth

import scala.util.Success

trait SecuredDirectives {
  self: Directives =>

  def check: CheckAuth
  def checkAuth: Directive1[Account] = {
    val invalidCredential = complete(StatusCodes.Unauthorized, "Invalid credentials.")
    optionalHeaderValueByName("X-Auth-Token").flatMap {
      case Some(token) => onComplete(check.invoke(CheckAuthInput(token)).value).flatMap {
        case Success(Right(o)) => provide(o.account)
        case _ => invalidCredential
      }
      case _ => invalidCredential
    }
  }
}
