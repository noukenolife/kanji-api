package com.noukenolife.kanji.auth.jwt.error

import com.noukenolife.kanji.support.InfraError

case class InvalidToken(message: String = "Invalid token.") extends InfraError
