package com.foodtechlab.ftlandroiduikit.textfield.extendable

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.FTLExtendableTextViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLExtendableTextViewThemeManager(
    override var darkTheme: FTLExtendableTextViewTheme? = FTLExtendableTextViewTheme(
        R.color.TextPrimaryDark,
        R.color.TextOnColorAdditionalLight
    ),
    override var lightTheme: FTLExtendableTextViewTheme = FTLExtendableTextViewTheme(
        R.color.TextPrimaryLight,
        R.color.TextOnColorAdditionalLight
    )
) : ViewThemeManager<FTLExtendableTextViewTheme>()