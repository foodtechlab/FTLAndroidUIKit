package com.foodtechlab.ftlandroiduikit.bar

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLBottomNavigationViewThemeManager(
    override var darkTheme: FTLBottomNavigationViewTheme? = FTLBottomNavigationViewTheme(
        R.color.SurfaceSecondDark,
        R.color.selector_ftl_bnv_item_color_dark
    ),
    override var lightTheme: FTLBottomNavigationViewTheme = FTLBottomNavigationViewTheme(
        R.color.SurfaceSecondLight,
        R.color.selector_ftl_bnv_item_color_light
    )
) : ViewThemeManager<FTLBottomNavigationViewTheme>() {
}