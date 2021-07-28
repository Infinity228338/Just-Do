package com.domore.justdo

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.getTimeFormatted(): String {
    val formatter = SimpleDateFormat(
        "hh:mm:ss",
        Locale.getDefault()
    )
    return formatter.format(this.time)
}

fun Calendar.getDateFormatted(): String {
    val formatter = SimpleDateFormat(
        "dd.MM.yy",
        Locale.getDefault()
    )
    return formatter.format(this.time)
}