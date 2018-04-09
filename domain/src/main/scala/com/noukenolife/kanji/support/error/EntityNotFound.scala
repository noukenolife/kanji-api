package com.noukenolife.kanji.support.error

case class EntityNotFound(message: String = "Entity not found.") extends DomainError
