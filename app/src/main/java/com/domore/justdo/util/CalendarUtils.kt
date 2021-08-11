package com.domore.justdo.util

import java.text.SimpleDateFormat
import java.util.*

const val TIME_PATTERN = "hh:mm:ss"
const val DATE_PATTERN = "dd.MM.yy"

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

fun Date.getDateFormatted(): String {
    val formatter = SimpleDateFormat(
        DATE_PATTERN,
        Locale.getDefault()
    )
    return formatter.format(this)
}

fun Date.getTimeFormatted(): String {
    val formatter = SimpleDateFormat(
        TIME_PATTERN,
        Locale.getDefault()
    )
    return formatter.format(this)
}