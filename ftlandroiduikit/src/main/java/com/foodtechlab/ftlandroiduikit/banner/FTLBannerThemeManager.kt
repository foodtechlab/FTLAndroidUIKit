package com.foodtechlab.ftlandroiduikit.banner

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLBannerThemeManager(
    override var darkTheme: FTLBannerTheme? = FTLBannerTheme(
        R.color.TextPrimaryDark,
        R.color.TextOnColorAdditionalDark,
        R.color.BannerBackgroundDefaultDark
    ),
    override var lightTheme: FTLBannerTheme = FTLBannerTheme(
        R.color.TextPrimaryLight,
        R.color.TextOnColorAdditionalLight,
        R.color.BannerBackgroundDefaultLight
    )
) : ViewThemeManager<FTLBannerTheme>()
