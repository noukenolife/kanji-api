package com.noukenolife.kanji.auth.module

import com.google.inject.AbstractModule
import com.noukenolife.kanji.auth.jwt.service.JWTAuth
import com.noukenolife.kanji.auth.service.Auth
import net.codingwell.scalaguice.ScalaModule

class AuthModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[Auth].to[JWTAuth]
  }
}
