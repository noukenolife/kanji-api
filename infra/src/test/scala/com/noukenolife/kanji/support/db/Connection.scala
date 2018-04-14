package com.noukenolife.kanji.support.db

import com.noukenolife.kanji.support.db.jdbc.DefaultDatabase
import org.scalatest.{BeforeAndAfterAll, Suite}

trait Connection extends BeforeAndAfterAll {
  self: Suite =>

  override protected def beforeAll(): Unit = {
    DefaultDatabase.init()
  }

  override protected def afterAll(): Unit = {
    DefaultDatabase.destroy()
  }
}
