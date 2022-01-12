package com.foodtechlab.ftlandroiduikit.tab.segmented

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLSegmentedTabLayoutThemeManager(
    override var darkTheme: FTLSegmentedTabLayoutTheme? = null,
    override var lightTheme: FTLSegmentedTabLayoutTheme = FTLSegmentedTabLayoutTheme(
        R.color.TabBackgroundLight,
        R.color.Surface
    )
) : ViewThemeManager<FTLSegmentedTabLayoutTheme>()
