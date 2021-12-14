package com.foodtechlab.ftlandroiduikit.dialog

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLProgressDialogThemeManager(
    override var darkTheme: FTLProgressDialogTheme? = FTLProgressDialogTheme(
        R.color.TextOnColorAdditionalDark,
        R.color.SurfaceFourthDark,
        R.color.ProgressPrimaryDark
    ),
    override var lightTheme: FTLProgressDialogTheme = FTLProgressDialogTheme(
        R.color.TextOnColorAdditionalLight,
        R.color.SurfaceFourthLight,
        R.color.ProgressPrimaryLight
    )
) : ViewThemeManager<FTLProgressDialogTheme>()
