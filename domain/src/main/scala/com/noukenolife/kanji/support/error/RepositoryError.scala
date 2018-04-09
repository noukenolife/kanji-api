package com.noukenolife.kanji.support.error

case class RepositoryError(message: String = "A repository error occurred.") extends DomainError
