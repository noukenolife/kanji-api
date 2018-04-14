package com.noukenolife.kanji.support.db.jdbc
import scalikejdbc.{Commons2ConnectionPoolFactory, ConnectionPoolFactory}

object DefaultDatabase extends Database {
  override implicit def cpFactory: ConnectionPoolFactory = Commons2ConnectionPoolFactory
}
