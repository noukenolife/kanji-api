package com.noukenolife.kanji.support

trait Entity[I <: Id[_]] {
  def id: I
}
