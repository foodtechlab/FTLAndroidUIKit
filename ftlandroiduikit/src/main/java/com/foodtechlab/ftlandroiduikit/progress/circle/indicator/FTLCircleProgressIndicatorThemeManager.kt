package com.foodtechlab.ftlandroiduikit.progress.circle.indicator

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLCircleProgressIndicatorThemeManager(
    override var darkTheme: FTLCircleProgressIndicatorTheme? = FTLCircleProgressIndicatorTheme(R.color.SurfaceFourthDark),
    override var lightTheme: FTLCircleProgressIndicatorTheme = FTLCircleProgressIndicatorTheme(R.color.SurfaceFourthLight)
) : ViewThemeManager<FTLCircleProgressIndicatorTheme>()
