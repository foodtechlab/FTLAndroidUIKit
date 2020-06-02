package com.foodtechlab.ftlandroiduikit.textfield.time.helper

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private const val timeFormat = "%02d:%02d"
private const val dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS"

fun formatTime(millis: Long) = String.format(
    timeFormat,
    TimeUnit.MILLISECONDS.toHours(millis) % 24,
    TimeUnit.MILLISECONDS.toMinutes(millis) % 60
)

@SuppressLint("SimpleDateFormat")
fun formatTime(dateStr: String?): String? = dateStr?.let {
    SimpleDateFormat(dateFormat).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }.parse(dateStr)?.let { formatTime(it.time) }
}