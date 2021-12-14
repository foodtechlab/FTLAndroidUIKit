package com.foodtechlab.ftlandroiduikit.shimmer

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLShimmerFrameLayoutThemeManager(
    override var darkTheme: FTLShimmerFrameLayoutTheme? = FTLShimmerFrameLayoutTheme(R.color.ShimmerBaseDark, R.color.ShimmerHighlightingDark),
    override var lightTheme: FTLShimmerFrameLayoutTheme = FTLShimmerFrameLayoutTheme(R.color.ShimmerBaseLight, R.color.ShimmerHighlightingLight)
    ) : ViewThemeManager<FTLShimmerFrameLayoutTheme>() {
}