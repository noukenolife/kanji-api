package com.noukenolife.kanji.auth.dto

import com.noukenolife.kanji.auth.entity.Account
import com.noukenolife.kanji.support.{Input, Output}

object CheckAuthIO {

  case class CheckAuthInput(token: String) extends Input
  case class CheckAuthOutput(account: Account) extends Output
}
