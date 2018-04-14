package com.noukenolife.kanji.support.db.jdbc

import com.noukenolife.kanji.support.db.cp.hikari.HikariConnectionPoolFactory
import scalikejdbc.ConnectionPoolFactory

object HikariDatabase extends Database {
  override implicit val cpFactory: ConnectionPoolFactory = HikariConnectionPoolFactory
}
