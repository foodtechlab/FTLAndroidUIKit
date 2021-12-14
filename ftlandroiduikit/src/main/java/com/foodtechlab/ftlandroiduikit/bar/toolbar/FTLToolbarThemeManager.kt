package com.foodtechlab.ftlandroiduikit.bar.toolbar

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLToolbarThemeManager(
    override var darkTheme: FTLToolbarTheme? = FTLToolbarTheme(
        R.color.SurfaceSecondDark,
        R.color.TextPrimaryDark,
        R.color.TextSuccessEnabledDark,
        R.color.TextInfoEnabledDark,
        R.color.IconSecondaryDark,
        R.color.IconSecondaryDark,
        R.color.ErrorSuccessDark,
        R.color.ErrorDangerDark,
        R.color.ErrorSuccessDark,
        R.color.ErrorDangerDark
    ),
    override var lightTheme: FTLToolbarTheme = FTLToolbarTheme(
        R.color.SurfaceSecondLight,
        R.color.TextPrimaryLight,
        R.color.TextSuccessEnabledLight,
        R.color.TextInfoEnabledLight,
        R.color.IconSecondaryLight,
        R.color.IconSecondaryLight,
        R.color.ErrorSuccessLight,
        R.color.ErrorDangerLight,
        R.color.ErrorSuccessLight,
        R.color.ErrorDangerLight
    )
) : ViewThemeManager<FTLToolbarTheme>()
