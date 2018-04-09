package com.noukenolife.kanji.support.module

import com.google.inject.AbstractModule
import com.noukenolife.kanji.support.Transactor
import com.noukenolife.kanji.support.db.TransactorImpl
import net.codingwell.scalaguice.ScalaModule

class InfraModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[Transactor].to[TransactorImpl]
  }
}
