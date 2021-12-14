package com.foodtechlab.ftlandroiduikit.textfield.section

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLSectionTextViewThemeManager(
    override var darkTheme: FTLSectionTextViewTheme? = FTLSectionTextViewTheme(
        R.color.TextPrimaryDark,
        R.color.IconSecondaryDark
    ),
    override var lightTheme: FTLSectionTextViewTheme = FTLSectionTextViewTheme(
        R.color.TextPrimaryLight,
        R.color.IconSecondaryLight
    )
) : ViewThemeManager<FTLSectionTextViewTheme>()