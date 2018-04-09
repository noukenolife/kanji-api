package com.noukenolife.kanji.support

import com.noukenolife.kanji.support.db.{CRUDMapper, Record}
import scalikejdbc.WrappedResultSet
import skinny.orm.Alias

package object record {

  case class FakeRecord(id: Long, value: String) extends Record

  object FakeRecord extends CRUDMapper[FakeRecord] {
    override val tableName: String = "fakes"
    override val columnNames: Seq[String] = Seq("id", "value")
    override val defaultAlias: Alias[FakeRecord] = createAlias("r")

    override def toNamedValues(record: FakeRecord): Seq[(Symbol, Any)] = Seq(
      'id -> record.id,
      'value -> record.value
    )

    override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[FakeRecord]): FakeRecord = {
      new FakeRecord(rs.get(n.id), rs.get(n.value))
    }
  }
}
