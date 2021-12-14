package com.foodtechlab.ftlandroiduikit.textfield.default

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLDefaultTextViewThemeManager(
    override var darkTheme: FTLDefaultTextViewTheme? = FTLDefaultTextViewTheme(R.color.TextPrimaryDark),
    override var lightTheme: FTLDefaultTextViewTheme = FTLDefaultTextViewTheme(R.color.TextPrimaryLight)
) : ViewThemeManager<FTLDefaultTextViewTheme>()
