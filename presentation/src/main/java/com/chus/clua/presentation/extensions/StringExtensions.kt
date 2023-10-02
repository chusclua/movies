package com.chus.clua.presentation.extensions

import java.text.SimpleDateFormat
import java.util.Calendar


fun String.toYear(): String {
    return try {
        Calendar.getInstance().apply {
            this.time = SimpleDateFormat(DATE_FORMAT).parse(this@toYear)
        }.get(Calendar.YEAR).toString()
    } catch (e: Exception) {
        ""
    }
}

private const val DATE_FORMAT = "yyyy-MM-dd"