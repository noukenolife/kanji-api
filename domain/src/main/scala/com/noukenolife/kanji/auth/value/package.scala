package com.noukenolife.kanji.auth

import com.noukenolife.kanji.support.Id

package object value {

  case class AccountId(value: Long) extends Id[Long]
  case class Email(value: String)
  case class Password(value: String)
  case class Credential(email: Email, password: Password)
}
