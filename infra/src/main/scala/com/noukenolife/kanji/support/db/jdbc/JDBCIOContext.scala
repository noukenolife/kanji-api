package com.noukenolife.kanji.support.db.jdbc

import com.noukenolife.kanji.support.IOContext
import scalikejdbc.{AutoSession, DBSession}

case class JDBCIOContext(session: DBSession = AutoSession) extends IOContext
