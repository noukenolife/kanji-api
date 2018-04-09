package com.noukenolife.kanji.support

import com.google.inject.{Guice, Injector}
import com.noukenolife.kanji.auth.module.AuthModule
import com.noukenolife.kanji.module.{AppServiceModule, ExecutionContextModule, RepositoryModule}

import scala.concurrent.ExecutionContext

object DI {
  def createInjector()(implicit ec: ExecutionContext): Injector = Guice.createInjector(
    new ExecutionContextModule(ec),
    new RepositoryModule(),
    new AppServiceModule(),
    new AuthModule()
  )
}
