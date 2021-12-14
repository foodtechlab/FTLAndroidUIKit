package com.foodtechlab.ftlandroiduikit.common

import androidx.annotation.StringRes
import com.foodtechlab.ftlandroiduikit.R

/**
 * Created by Umalt on 15.06.2020
 */
enum class NetworkConnectivityState(@StringRes val message: Int) {
    CONNECTED(
        R.string.connectivity_state_connected
    ),
    DISCONNECTED(
        R.string.connectivity_state_disconnected
    )
}