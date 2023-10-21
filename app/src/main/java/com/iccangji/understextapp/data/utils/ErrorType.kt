package com.iccangji.understextapp.data.utils

import androidx.annotation.StringRes
import com.iccangji.understextapp.R

enum class ErrorType(@StringRes val message: Int){
    NO_INTERNET(message = R.string.no_internet_connection),
    NO_TEXT(message = R.string.no_text),
    TOO_SHORT_TO_SUMMARIZE(message = R.string.too_short_to_summarize)
}
