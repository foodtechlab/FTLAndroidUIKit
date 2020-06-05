package com.foodtechlab.ftlandroiduikit.textfield.time.helper

import androidx.annotation.FontRes
import com.foodtechlab.ftlandroiduikit.R

enum class Size(
    val paddingHorizontal: Float,
    val paddingVertical: Float,
    var radius: Float,
    var textSize: Float,
    var iconSize: Float,
    var iconMarginEnd: Float,
    @FontRes var font: Int
) {
    SMALL(8f, 8f, 16f, 16f, 16f, 4f, R.font.roboto_medium),
    LARGE(16f, 20f, 32f, 32f, 24f, 12f, R.font.roboto_regular),
}