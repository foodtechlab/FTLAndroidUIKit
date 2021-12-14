package com.foodtechlab.ftlandroiduikit.textfield.table

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLTableHeaderViewTheme(
    override var darkTheme: FTLTableHeaderTheme? = FTLTableHeaderTheme(
        R.color.DividerPrimaryDark,
        R.color.IconSecondaryDark,
        R.color.TextPrimaryDark,
        R.color.TextOnColorAdditionalDark
    ),
    override var lightTheme: FTLTableHeaderTheme = FTLTableHeaderTheme(
        R.color.DividerPrimaryLight,
        R.color.IconSecondaryLight,
        R.color.TextPrimaryLight,
        R.color.TextOnColorAdditionalLight
    )
) : ViewThemeManager<FTLTableHeaderTheme>()
