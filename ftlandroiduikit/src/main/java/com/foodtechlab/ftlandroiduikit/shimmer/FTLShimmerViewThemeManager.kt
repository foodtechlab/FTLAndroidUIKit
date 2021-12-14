package com.foodtechlab.ftlandroiduikit.shimmer

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLShimmerViewThemeManager(
    override var darkTheme: FTLShimmerViewTheme? = FTLShimmerViewTheme(R.color.ShimmerBackgroundDark),
    override var lightTheme: FTLShimmerViewTheme = FTLShimmerViewTheme(R.color.ShimmerBackgroundLight)
) : ViewThemeManager<FTLShimmerViewTheme>()
