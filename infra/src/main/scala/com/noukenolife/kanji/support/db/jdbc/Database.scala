package com.noukenolife.kanji.support.db.jdbc

import com.noukenolife.kanji.support.db.cp.config.DatabaseConfigFactory
import com.typesafe.config.ConfigFactory
import scalikejdbc.{ConnectionPool, ConnectionPoolFactory}

trait Database {

  implicit def cpFactory: ConnectionPoolFactory

  def init(): Unit = {
    val config = DatabaseConfigFactory.fromConfig(ConfigFactory.load)
    Class.forName(config.driver)
    ConnectionPool.singleton(
      url = config.dataSource.url,
      user = config.dataSource.user,
      password = config.dataSource.password
    )
  }

  def destroy(): Unit = ConnectionPool.closeAll()
}
