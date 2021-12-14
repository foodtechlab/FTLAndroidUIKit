package com.foodtechlab.ftlandroiduikit.progress.linear.indicator

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLLinearProgressIndicatorThemeManager(
    override var darkTheme: FTLLinearProgressIndicatorTheme? = FTLLinearProgressIndicatorTheme(
        R.color.ProgressBackgroundDark,
        R.color.ProgressPrimaryDark
    ),
    override var lightTheme: FTLLinearProgressIndicatorTheme = FTLLinearProgressIndicatorTheme(
        R.color.ProgressBackgroundLight,
        R.color.ProgressPrimaryLight
    )
) : ViewThemeManager<FTLLinearProgressIndicatorTheme>()
