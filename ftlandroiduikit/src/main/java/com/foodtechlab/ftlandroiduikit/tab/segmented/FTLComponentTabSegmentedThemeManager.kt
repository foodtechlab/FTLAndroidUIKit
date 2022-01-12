package com.foodtechlab.ftlandroiduikit.tab.segmented

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLComponentTabSegmentedThemeManager(
    override var darkTheme: FTLComponentTabSegmentedTheme? = null,
    override var lightTheme: FTLComponentTabSegmentedTheme = FTLComponentTabSegmentedTheme(
        R.color.TextInfoEnabledLight,
        R.color.TabTextLight,
        R.color.TabSelectedTextLight,
    )
) : ViewThemeManager<FTLComponentTabSegmentedTheme>()
