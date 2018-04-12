package com.noukenolife.kanji.auth.db

import com.noukenolife.kanji.support.db.dao.CRUDMapper
import com.noukenolife.kanji.support.db.record.Record
import scalikejdbc._
import skinny.orm.Alias

package object record {

  case class AccountRecord(id: Long, email: String, password: String) extends Record

  object AccountRecord extends CRUDMapper[AccountRecord] {
    override val tableName: String = "accounts"
    override val columnNames: Seq[String] = Seq("id", "email", "password")
    override val defaultAlias: Alias[AccountRecord] = createAlias("a")

    override def toNamedValues(record: AccountRecord): Seq[(Symbol, Any)] = Seq(
      'id -> record.id,
      'email -> record.email,
      'password -> record.password
    )

    override def extract(rs: WrappedResultSet, n: ResultName[AccountRecord]): AccountRecord = {
      new AccountRecord(rs.get(n.id), rs.get(n.email), rs.get(n.password))
    }
  }

}
