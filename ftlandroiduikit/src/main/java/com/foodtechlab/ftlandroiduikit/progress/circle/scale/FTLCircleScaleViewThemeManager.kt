package com.foodtechlab.ftlandroiduikit.progress.circle.scale

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLCircleScaleViewThemeManager(
    override var darkTheme: FTLCircleScaleViewTheme? = FTLCircleScaleViewTheme(
        R.color.ProgressBackgroundDark,
        R.color.ProgressPrimaryDark,
        R.color.IconSecondaryDark
    ),
    override var lightTheme: FTLCircleScaleViewTheme =
        FTLCircleScaleViewTheme(
            R.color.ProgressBackgroundLight,
            R.color.ProgressPrimaryLight,
            R.color.IconSecondaryLight
        )
) : ViewThemeManager<FTLCircleScaleViewTheme>()
