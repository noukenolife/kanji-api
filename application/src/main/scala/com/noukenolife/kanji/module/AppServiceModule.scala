package com.noukenolife.kanji.module

import com.google.inject.AbstractModule
import com.noukenolife.kanji.auth.service.{CheckAuth, CheckAuthImpl, SignIn, SignInImpl}
import net.codingwell.scalaguice.ScalaModule

class AppServiceModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[SignIn].to[SignInImpl]
    bind[CheckAuth].to[CheckAuthImpl]
  }
}
