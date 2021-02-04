package com.foodtechlab.ftlandroiduikit.bar.toolbar

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

/**
 * Created by Umalt on 15.06.2020
 */
enum class NetworkConnectivityState(@ColorRes var color: Int, @StringRes val message: Int) {
    CONNECTED(
        ThemeManager.theme.ftlToolbarTheme.networkConnected,
        R.string.connectivity_state_connected
    ),
    DISCONNECTED(
        ThemeManager.theme.ftlToolbarTheme.networkDisconnected,
        R.string.connectivity_state_disconnected
    )
}