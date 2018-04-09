package com.noukenolife.kanji.auth.entity

import com.noukenolife.kanji.auth.value.{AccountId, Email, Password}
import com.noukenolife.kanji.support.Entity

case class Account(id: AccountId, email: Email, password: Password) extends Entity[AccountId]
