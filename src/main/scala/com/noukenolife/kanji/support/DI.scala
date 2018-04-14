package com.noukenolife.kanji.support

import com.google.inject.{Guice, Injector}
import com.noukenolife.kanji.module.{AppModule, ExecutionContextModule, InfraModule}

import scala.concurrent.ExecutionContext

object DI {
  def createInjector()(implicit ec: ExecutionContext): Injector = Guice.createInjector(
    new ExecutionContextModule(ec),
    new AppModule(),
    new InfraModule()
  )
}
