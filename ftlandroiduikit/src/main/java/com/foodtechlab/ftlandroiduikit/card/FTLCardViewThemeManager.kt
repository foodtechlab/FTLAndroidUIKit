package com.foodtechlab.ftlandroiduikit.card

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLCardViewThemeManager(
    override var darkTheme: FTLCardViewTheme? = FTLCardViewTheme(R.color.SurfaceFirstDark),
    override var lightTheme: FTLCardViewTheme = FTLCardViewTheme(R.color.SurfaceFirstLight)
) : ViewThemeManager<FTLCardViewTheme>()
