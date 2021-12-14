package com.foodtechlab.ftlandroiduikit.container.linear

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLLinearLayoutThemeManager(
    override var darkTheme: FTLLinearLayoutTheme? = FTLLinearLayoutTheme(R.color.BackgroundDefaultDark),
    override var lightTheme: FTLLinearLayoutTheme = FTLLinearLayoutTheme(R.color.BackgroundDefaultLight)
) : ViewThemeManager<FTLLinearLayoutTheme>()
