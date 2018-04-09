package com.noukenolife.kanji.support

import com.github.swagger.akka.SwaggerHttpService
import com.github.swagger.akka.model.Info
import com.noukenolife.kanji.auth.controllers.AuthController

trait SwaggerDocService extends SwaggerHttpService {
  override val apiClasses: Set[Class[_]] = Set(
    classOf[AuthController]
  )
  override val host: String = "localhost:9000"
  override val info: Info = Info(version = "1.0.0")
  override val apiDocsPath: String = "docs"
}

object SwaggerDocService extends SwaggerDocService
