package com.foodtechlab.ftlandroiduikit.bar.toolbar

import androidx.annotation.ColorRes
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

/**
 * Created by Umalt on 28.05.2020
 */
enum class SocketConnectivityState(@ColorRes var color: Int) {
    CONNECTED(ThemeManager.theme.ftlToolbarTheme.socketConnected),
    DISCONNECTED(ThemeManager.theme.ftlToolbarTheme.socketDisconnected)
}