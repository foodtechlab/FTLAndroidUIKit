package com.foodtechlab.ftlandroiduikit.button.image

import androidx.annotation.DrawableRes
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

enum class ImageButtonType(@DrawableRes var bgRes: Int, val size: Int) {
    NAVIGATOR_SMALL(ThemeManager.theme.ftlImageButtonTheme.navigatorSmall, 32),
    NAVIGATOR_MEDIUM(ThemeManager.theme.ftlImageButtonTheme.navigatorMedium, 40),
    NAVIGATOR_LARGE(ThemeManager.theme.ftlImageButtonTheme.navigatorLarge, 48),
    LOCATION_SMALL(ThemeManager.theme.ftlImageButtonTheme.locationSmall, 32),
    LOCATION_MEDIUM(ThemeManager.theme.ftlImageButtonTheme.locationMedium, 40),
    LOCATION_LARGE(ThemeManager.theme.ftlImageButtonTheme.locationLarge, 48),
    INFO_SMALL(ThemeManager.theme.ftlImageButtonTheme.infoSmall, 32),
    INFO_MEDIUM(ThemeManager.theme.ftlImageButtonTheme.infoMedium, 40),
    INFO_LARGE(ThemeManager.theme.ftlImageButtonTheme.infoLarge, 48),
    REPLAY_SMALL(ThemeManager.theme.ftlImageButtonTheme.replaySmall, 32),
    REPLAY_MEDIUM(ThemeManager.theme.ftlImageButtonTheme.replayMedium, 40),
    REPLAY_LARGE(ThemeManager.theme.ftlImageButtonTheme.replayLarge, 48)
}