package com.foodtechlab.ftlandroiduikit.textfield.time.helper

import java.util.concurrent.TimeUnit

private const val timeFormat = "%02d:%02d"

fun formatTime(millis: Long) = String.format(
    timeFormat,
    TimeUnit.MILLISECONDS.toHours(millis),
    TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1)
)