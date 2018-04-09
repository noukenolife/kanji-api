package com.noukenolife.kanji.support.error

import com.noukenolife.kanji.support.InfraError

case class DAOError(message: String = "A DAO error occurred.") extends InfraError