package com.noukenolife.kanji.support.entity

import com.noukenolife.kanji.support.Entity
import com.noukenolife.kanji.support.value.FakeId

case class FakeEntity(id: FakeId, value: String) extends Entity[FakeId]
