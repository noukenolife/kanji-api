package com.noukenolife.kanji.auth.dto

import com.noukenolife.kanji.auth.value.{Credential, Email, Password}
import com.noukenolife.kanji.support.{Input, Output}

object SignInIO {
  case class SignInInput(email: String, password: String) extends Input {
    def credential: Credential = Credential(Email(email), Password(password))
  }

  case class SignInOutput(token: String) extends Output
}
