package com.noukenolife.kanji.support.db.cp.hikari

import scalikejdbc.{ConnectionPool, ConnectionPoolFactory, ConnectionPoolSettings}

object HikariConnectionPoolFactory extends ConnectionPoolFactory {
  override def apply(url: String, user: String, password: String,
    settings: ConnectionPoolSettings = ConnectionPoolSettings()): ConnectionPool = {

    new HikariConnectionPool(url, user, password, settings)
  }
}
