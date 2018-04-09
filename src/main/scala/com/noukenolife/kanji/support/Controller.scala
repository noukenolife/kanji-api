package com.noukenolife.kanji.support

import akka.http.scaladsl.server.{Directives, Route}

trait Controller extends Directives with DefaultJsonSupport {
  def route: Route
}
