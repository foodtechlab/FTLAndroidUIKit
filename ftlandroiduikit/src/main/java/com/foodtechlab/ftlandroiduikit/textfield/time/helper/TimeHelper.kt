package com.foodtechlab.ftlandroiduikit.textfield.time.helper

import java.text.SimpleDateFormat
import java.util.*

private const val timeFormat = "hh:mm"
private const val dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS"

fun formatTime(millis: Long, timeZoneId: String?) =
    SimpleDateFormat(timeFormat, Locale.getDefault())
        .apply {
            timeZone = timeZoneId?.let { TimeZone.getTimeZone(timeZoneId) } ?: TimeZone.getDefault()
        }
        .format(Date(millis))

fun formatTime(dateStr: String?, timeZoneId: String?) = dateStr?.let {
    SimpleDateFormat(dateFormat, Locale.getDefault())
        .apply {
            timeZone = timeZoneId?.let { TimeZone.getTimeZone(timeZoneId) } ?: TimeZone.getDefault()
        }
        .parse(dateStr)?.let { formatTime(it.time, timeZoneId) }
}

fun getMillis(dateStr: String?, timeZoneId: String?) = dateStr?.let {
    SimpleDateFormat(dateFormat, Locale.getDefault())
        .apply {
            timeZone = timeZoneId?.let { TimeZone.getTimeZone(timeZoneId) } ?: TimeZone.getDefault()
        }
        .parse(dateStr)?.time ?: 0L
} ?: 0L