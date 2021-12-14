package com.foodtechlab.ftlandroiduikit.tab

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLTabLayoutThemeManager(
    override var darkTheme: FTLTabLayoutTheme? = FTLTabLayoutTheme(
        R.color.SurfaceFourthDark,
        R.color.IconInfoDark,
        R.color.TextPrimaryDark,
        R.color.ButtonInfoPressedDark,
        R.color.ErrorDangerDark,
        R.color.ErrorSuccessDark
    ),
    override var lightTheme: FTLTabLayoutTheme = FTLTabLayoutTheme(
        R.color.SurfaceFourthLight,
        R.color.IconInfoLight,
        R.color.TextPrimaryLight,
        R.color.ButtonInfoPressedLight,
        R.color.ErrorDangerLight,
        R.color.ErrorSuccessLight
    )
) : ViewThemeManager<FTLTabLayoutTheme>()
