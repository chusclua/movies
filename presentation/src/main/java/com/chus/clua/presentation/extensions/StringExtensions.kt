package com.chus.clua.presentation.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun String.toYear(): String {
    return try {
        Calendar.getInstance().apply {
            this.time = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(this@toYear)
        }.get(Calendar.YEAR).toString()
    } catch (e: Exception) {
        ""
    }
}

fun String.toPrettyDate(): String {
    return try {
        val date = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(this)
        return SimpleDateFormat(PRETTY_DATE_FORMAT, Locale.getDefault()).format(date)
    } catch (e: Exception) {
        ""
    }
}

private const val DATE_FORMAT = "yyyy-MM-dd"
private const val PRETTY_DATE_FORMAT = "dd MMMM yyyy"