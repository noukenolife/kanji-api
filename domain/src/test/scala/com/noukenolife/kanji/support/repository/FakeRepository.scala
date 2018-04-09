package com.noukenolife.kanji.support.repository

import com.noukenolife.kanji.support.Repository
import com.noukenolife.kanji.support.entity.FakeEntity
import com.noukenolife.kanji.support.value.FakeId

trait FakeRepository extends Repository[FakeId, FakeEntity]
