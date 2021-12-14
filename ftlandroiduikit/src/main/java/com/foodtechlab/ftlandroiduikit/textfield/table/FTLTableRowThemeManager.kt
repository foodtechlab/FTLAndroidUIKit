package com.foodtechlab.ftlandroiduikit.textfield.table

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLTableRowThemeManager(
    override var darkTheme: FTLTableRowTheme? = FTLTableRowTheme(
        R.color.TextPrimaryDark,
        R.color.TextOnColorAdditionalDark,
        R.color.TextPrimaryDark
    ),
    override var lightTheme: FTLTableRowTheme = FTLTableRowTheme(
        R.color.TextPrimaryLight,
        R.color.TextOnColorAdditionalLight,
        R.color.TextPrimaryLight
    )
) : ViewThemeManager<FTLTableRowTheme>()
