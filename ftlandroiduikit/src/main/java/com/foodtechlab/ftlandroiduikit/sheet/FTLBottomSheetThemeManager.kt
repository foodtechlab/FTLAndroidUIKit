package com.foodtechlab.ftlandroiduikit.sheet

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLBottomSheetThemeManager(
    override var darkTheme: FTLBottomSheetTheme? = FTLBottomSheetTheme(
        R.color.TextPrimaryDark,
        R.color.SurfaceFourthDark
    ),
    override var lightTheme: FTLBottomSheetTheme =
        FTLBottomSheetTheme(R.color.TextPrimaryLight, R.color.SurfaceFourthLight)
) : ViewThemeManager<FTLBottomSheetTheme>()
