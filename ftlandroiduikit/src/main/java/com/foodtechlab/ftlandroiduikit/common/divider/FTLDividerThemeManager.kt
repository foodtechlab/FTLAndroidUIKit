package com.foodtechlab.ftlandroiduikit.common.divider

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLDividerThemeManager(
    override var darkTheme: FTLDividerTheme? = FTLDividerTheme(R.color.DividerPrimaryDark),
    override var lightTheme: FTLDividerTheme = FTLDividerTheme(R.color.DividerPrimaryLight)
) : ViewThemeManager<FTLDividerTheme>()
