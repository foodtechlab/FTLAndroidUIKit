package com.foodtechlab.ftlandroiduikit.container.coordinator

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLCoordinatorLayoutThemeManager(
    override var darkTheme: FTLCoordinatorLayoutTheme? = FTLCoordinatorLayoutTheme(R.color.BackgroundSecondaryDark),
    override var lightTheme: FTLCoordinatorLayoutTheme = FTLCoordinatorLayoutTheme(R.color.BackgroundSecondaryLight)
) : ViewThemeManager<FTLCoordinatorLayoutTheme>()
