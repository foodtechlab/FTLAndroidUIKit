package com.foodtechlab.ftlandroiduikit.button.floating

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLFloatingActionButtonThemeManager(
    override var darkTheme: FTLFloatingActionButtonTheme? = FTLFloatingActionButtonTheme(R.color.selector_ftl_fab_dark),
    override var lightTheme: FTLFloatingActionButtonTheme = FTLFloatingActionButtonTheme(R.color.selector_ftl_fab_light)
) : ViewThemeManager<FTLFloatingActionButtonTheme>()