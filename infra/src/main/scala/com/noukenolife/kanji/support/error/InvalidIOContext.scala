package com.noukenolife.kanji.support.error

import com.noukenolife.kanji.support.InfraError

case class InvalidIOContext(message: String = "Invalid IO context.") extends InfraError