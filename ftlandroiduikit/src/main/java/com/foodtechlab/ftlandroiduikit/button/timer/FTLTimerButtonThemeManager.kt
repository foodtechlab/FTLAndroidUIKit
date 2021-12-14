package com.foodtechlab.ftlandroiduikit.button.timer

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLTimerButtonThemeManager(
    override var darkTheme: FTLTimerButtonTheme? = FTLTimerButtonTheme(
        R.color.TextPlaceholderDark,
        R.color.ButtonSecondaryPressedDark,
        R.color.ButtonSecondaryEnableDark,
        R.color.TextPlaceholderOpacity60Dark,
        R.color.TextPlaceholderDark,
        R.color.TextOnColorPrimaryDark,
        R.color.ButtonDangerPressedDark,
        R.color.ButtonDangerEnableDark,
        R.color.TextOnColorPrimaryOpacity60Dark,
        R.color.TextOnColorPrimaryDark,
        R.color.TextOnColorPrimaryDark,
        R.color.ButtonSuccessPressedDark,
        R.color.ButtonSuccessEnableDark,
        R.color.TextOnColorPrimaryOpacity60Dark,
        R.color.TextOnColorPrimaryDark
    ),
    override var lightTheme: FTLTimerButtonTheme = FTLTimerButtonTheme(
        R.color.TextPlaceholderLight,
        R.color.ButtonSecondaryPressedLight,
        R.color.ButtonSecondaryEnableLight,
        R.color.TextPlaceholderOpacity60Light,
        R.color.TextPlaceholderLight,
        R.color.TextOnColorPrimaryLight,
        R.color.ButtonDangerPressedLight,
        R.color.ButtonDangerEnableLight,
        R.color.TextOnColorPrimaryOpacity60Light,
        R.color.TextOnColorPrimaryLight,
        R.color.TextOnColorPrimaryLight,
        R.color.ButtonSuccessPressedLight,
        R.color.ButtonSuccessEnableLight,
        R.color.TextOnColorPrimaryOpacity60Light,
        R.color.TextOnColorPrimaryLight
    )
) : ViewThemeManager<FTLTimerButtonTheme>()
