package com.foodtechlab.ftlandroiduikit.textfield.menu

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLCellMenuThemeManager(
    override var darkTheme: FTLCellMenuTheme? = FTLCellMenuTheme(
        R.color.TextPrimaryDark,
        R.color.CellPressedDark
    ),
    override var lightTheme: FTLCellMenuTheme = FTLCellMenuTheme(
        R.color.TextPrimaryLight,
        R.color.CellPressedLight
    )
) : ViewThemeManager<FTLCellMenuTheme>()