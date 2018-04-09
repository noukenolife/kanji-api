package com.noukenolife.kanji.auth.controllers

import javax.inject.Inject
import javax.ws.rs.Path

import akka.http.scaladsl.server.Route
import com.noukenolife.kanji.auth.dto.SignInIO.SignInInput
import com.noukenolife.kanji.auth.service.{CheckAuth, SignIn}
import com.noukenolife.kanji.auth.support.{CustomDirectives, SecuredDirectives}
import com.noukenolife.kanji.support.Controller
import io.swagger.annotations.Api

@Api(value = "/auth", produces = "application/json")
@Path("/auth")
class AuthController @Inject() (
  signIn: SignIn,
  override val check: CheckAuth
) extends Controller with CustomDirectives with SecuredDirectives {

  override def route: Route = pathPrefix("auth") {
    path("signin") {
      post {
        entity(as[SignInInput]) { input =>
          action(signIn.invoke(input)) { output =>
            complete(output)
          }
        }
      }
    } ~
    path("signup") {
      complete("signup")
    }
  }
}
