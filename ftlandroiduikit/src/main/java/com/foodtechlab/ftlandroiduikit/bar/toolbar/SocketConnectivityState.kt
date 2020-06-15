package com.foodtechlab.ftlandroiduikit.bar.toolbar

import androidx.annotation.ColorRes
import com.foodtechlab.ftlandroiduikit.R

/**
 * Created by Umalt on 28.05.2020
 */
enum class SocketConnectivityState(@ColorRes val color: Int) {

    CONNECTED(R.color.AdditionalGreen),
    DISCONNECTED(R.color.AdditionalPink)
}