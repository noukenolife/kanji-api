package com.noukenolife.kanji.module

import com.google.inject.{AbstractModule, Provides}
import net.codingwell.scalaguice.ScalaModule

import scala.concurrent.ExecutionContext

class ExecutionContextModule(ec: ExecutionContext) extends AbstractModule with ScalaModule {
  override def configure(): Unit = {}

  @Provides
  def provideExecutionContext(): ExecutionContext = ec
}
