package com.foodtechlab.ftlandroiduikit.imageview.placeholder

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLPlaceholderImageViewThemeManager(
    override var darkTheme: FTLPlaceholderImageViewTheme? = FTLPlaceholderImageViewTheme(R.drawable.ic_restaurant_placeholder_dark),
    override var lightTheme: FTLPlaceholderImageViewTheme = FTLPlaceholderImageViewTheme(R.drawable.ic_restaurant_placeholder_light)
) : ViewThemeManager<FTLPlaceholderImageViewTheme>()
