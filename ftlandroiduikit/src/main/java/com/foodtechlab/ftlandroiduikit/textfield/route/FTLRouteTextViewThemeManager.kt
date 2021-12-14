package com.foodtechlab.ftlandroiduikit.textfield.route

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.FTLRouteTextViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLRouteTextViewThemeManager(
    override var darkTheme: FTLRouteTextViewTheme? = FTLRouteTextViewTheme(
        R.color.IconBackgroundBlueDark,
        R.color.IconPrimaryDark,
        R.color.TextPrimaryDark,
        R.color.TextPrimaryDark
    ),
    override var lightTheme: FTLRouteTextViewTheme = FTLRouteTextViewTheme(
        R.color.IconBackgroundBlueLight,
        R.color.IconPrimaryLight,
        R.color.TextPrimaryLight,
        R.color.TextPrimaryLight
    )
) : ViewThemeManager<FTLRouteTextViewTheme>()
