package com.foodtechlab.ftlandroiduikit.textfield.multiple

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLMultipleTextViewThemeManager(
    override var darkTheme: FTLMultipleTextViewTheme? = FTLMultipleTextViewTheme(R.color.TextPrimaryDark),
    override var lightTheme: FTLMultipleTextViewTheme =
        FTLMultipleTextViewTheme(R.color.TextPrimaryLight)
) : ViewThemeManager<FTLMultipleTextViewTheme>()