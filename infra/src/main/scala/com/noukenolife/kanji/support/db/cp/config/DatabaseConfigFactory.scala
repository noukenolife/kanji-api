package com.noukenolife.kanji.support.db.cp.config

import com.typesafe.config.Config
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._

object DatabaseConfigFactory {
  def fromConfig(config: Config): DatabaseConfig = {
    config.as[DatabaseConfig]("db")
  }
}
