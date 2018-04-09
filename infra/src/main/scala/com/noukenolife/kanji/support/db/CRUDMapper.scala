package com.noukenolife.kanji.support.db

import skinny.orm.SkinnyCRUDMapper

trait CRUDMapper[R <: Record] extends SkinnyCRUDMapper[R] {
  def toNamedValues(record: R): Seq[(Symbol, Any)]

  /**
    * Column names
    *
    * @note WTF Point: You need to force to specify column names,
    *       otherwise scalikejdbc tries to fetch column names from other tables with the same table name
    * @see https://github.com/scalikejdbc/scalikejdbc/blob/b530be9cbb43967533d16e0e1c27387eebad5f17/scalikejdbc-interpolation/src/main/scala/scalikejdbc/SQLSyntaxSupportFeature.scala#L154
    */
  override def columnNames: Seq[String]


}
