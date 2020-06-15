package com.foodtechlab.ftlandroiduikit.bar.toolbar

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.foodtechlab.ftlandroiduikit.R

/**
 * Created by Umalt on 15.06.2020
 */
enum class NetworkConnectivityState(@ColorRes val color: Int, @StringRes val message: Int) {
    CONNECTED(R.color.AdditionalGreen, R.string.connectivity_state_connected),
    DISCONNECTED(R.color.PrimaryDangerEnabled, R.string.connectivity_state_disconnected)
}