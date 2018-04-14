package com.noukenolife.kanji.module

import com.google.inject.AbstractModule
import com.noukenolife.kanji.auth.jwt.service.JWTAuth
import com.noukenolife.kanji.auth.repository.{AccountRepository, JDBCAccountRepository}
import com.noukenolife.kanji.auth.service.Auth
import com.noukenolife.kanji.support.Transactor
import com.noukenolife.kanji.support.db.jdbc.JDBCTransactorImpl
import net.codingwell.scalaguice.ScalaModule

class InfraModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {

    // Repositories
    bind[AccountRepository].to[JDBCAccountRepository]

    // Services
    bind[Transactor].to[JDBCTransactorImpl]
    bind[Auth].to[JWTAuth]
  }
}
