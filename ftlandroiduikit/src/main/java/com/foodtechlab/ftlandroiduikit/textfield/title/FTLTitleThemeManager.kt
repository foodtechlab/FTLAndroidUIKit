package com.foodtechlab.ftlandroiduikit.textfield.title

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLTitleThemeManager(
    override var darkTheme: FTLTitleTheme? = FTLTitleTheme(
        R.color.TextPrimaryDark,
        R.color.TextSuccessEnabledDark
    ),
    override var lightTheme: FTLTitleTheme =
        FTLTitleTheme(R.color.TextPrimaryLight, R.color.TextSuccessEnabledLight)
) : ViewThemeManager<FTLTitleTheme>()