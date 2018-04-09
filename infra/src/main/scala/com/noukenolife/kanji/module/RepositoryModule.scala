package com.noukenolife.kanji.module

import com.google.inject.{AbstractModule, Provides}
import com.noukenolife.kanji.auth.repository.{AccountRepository, JDBCAccountRepository}
import com.noukenolife.kanji.support.IOContext
import com.noukenolife.kanji.support.db.JDBCIOContext
import net.codingwell.scalaguice.ScalaModule

class RepositoryModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[AccountRepository].to[JDBCAccountRepository]
  }
}
