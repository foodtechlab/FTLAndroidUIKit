package com.foodtechlab.ftlandroiduikit.container.constraint

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLConstraintLayoutThemeManager(
    override var darkTheme: FTLConstraintLayoutTheme? = FTLConstraintLayoutTheme(R.color.BackgroundDefaultDark),
    override var lightTheme: FTLConstraintLayoutTheme = FTLConstraintLayoutTheme(R.color.BackgroundDefaultLight)
) : ViewThemeManager<FTLConstraintLayoutTheme>()
