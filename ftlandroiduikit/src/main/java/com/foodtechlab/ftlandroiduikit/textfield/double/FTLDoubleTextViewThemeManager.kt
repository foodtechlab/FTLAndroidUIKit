package com.foodtechlab.ftlandroiduikit.textfield.double

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLDoubleTextViewThemeManager(
    override var darkTheme: FTLDoubleTextViewTheme? = FTLDoubleTextViewTheme(R.color.TextPrimaryDark),
    override var lightTheme: FTLDoubleTextViewTheme = FTLDoubleTextViewTheme(R.color.TextPrimaryLight)
) : ViewThemeManager<FTLDoubleTextViewTheme>()
