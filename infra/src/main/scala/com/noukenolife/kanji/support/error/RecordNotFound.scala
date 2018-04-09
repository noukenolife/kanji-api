package com.noukenolife.kanji.support.error

import com.noukenolife.kanji.support.InfraError

case class RecordNotFound(message: String = "Record not found.") extends InfraError
