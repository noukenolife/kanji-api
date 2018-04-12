package com.noukenolife.kanji.support.repository

import com.noukenolife.kanji.support.db.dao.GenericDAO
import com.noukenolife.kanji.support.db.record.Record
import com.noukenolife.kanji.support.{Entity, Id, Repository}

trait GenericRepository[I <: Id[_], E <: Entity[I], R <: Record] extends Repository[I, E] {
  protected implicit def toEntity(record: R): E
  protected implicit def toRecord(entity: E): R
  protected def dao: GenericDAO[R]
}
