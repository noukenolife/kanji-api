package com.noukenolife.kanji.support

import com.noukenolife.kanji.error.CustomError

trait AppError extends CustomError {
  def code: Int
}
