package com.foodtechlab.ftlandroiduikit.textfield.time

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.Icon

class DeliveryTimeLargeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : DeliveryTimeView(context, attrs, defStyleAttr) {

    init {
        font = R.font.roboto_medium

        timeSize = 32f
        paddingStart = 16f
        paddingEnd = 24f
        verticalPadding = 16f
        cornerRadius = 32f

        iconUsual = Icon(
            24f,
            24f,
            12f,
            0f,
            ContextCompat.getDrawable(context, R.drawable.ic_clock_24)
        )

        iconUrgent = Icon(
            15f,
            24f,
            12f,
            0f,
            ContextCompat.getDrawable(context, R.drawable.ic_flash_large)
        )
    }
}