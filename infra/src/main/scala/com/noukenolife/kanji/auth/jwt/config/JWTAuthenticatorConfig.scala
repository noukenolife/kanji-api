package com.noukenolife.kanji.auth.jwt.config

import scala.concurrent.duration.Duration

case class JWTAuthenticatorConfig(
  secretKey: String = "secret",
  issuer: Option[String] = None,
  subject: Option[String] = None,
  audience: Option[Set[String]] = None,
  duration: Option[Duration] = None
)
