package com.noukenolife.kanji.support

import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

trait DefaultJsonSupport extends Json4sSupport {
  implicit val serialization = Serialization
  implicit val formats = DefaultFormats
}
