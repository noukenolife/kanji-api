package com.noukenolife.kanji.support.db.cp.hikari

import java.sql.Connection
import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import scalikejdbc.{ConnectionPool, ConnectionPoolSettings}

class HikariConnectionPool(
  override val url: String,
  override val user: String,
  password: String,
  override val settings: ConnectionPoolSettings = ConnectionPoolSettings()
) extends ConnectionPool(url, user, password, settings) {

  private[this] lazy val _dataSource: HikariDataSource = {
    val config = new HikariConfig()
    config.setJdbcUrl(url)
    config.setUsername(user)
    config.setPassword(password)
    new HikariDataSource(config)
  }

  override def dataSource: DataSource = _dataSource
  override def borrow(): Connection = _dataSource.getConnection
  override def close(): Unit = _dataSource.close()
}
