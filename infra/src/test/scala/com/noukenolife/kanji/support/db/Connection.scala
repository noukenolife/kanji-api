package com.noukenolife.kanji.support.db

import org.scalatest.{BeforeAndAfterAll, Suite}
import skinny.DBSettings

trait Connection extends BeforeAndAfterAll {
  self: Suite =>

  override protected def beforeAll(): Unit = {
    DBSettings.initialize()
  }

  override protected def afterAll(): Unit = {
    DBSettings.destroy()
  }
}
