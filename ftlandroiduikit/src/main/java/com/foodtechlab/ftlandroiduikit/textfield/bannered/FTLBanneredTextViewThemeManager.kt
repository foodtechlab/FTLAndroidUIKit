package com.foodtechlab.ftlandroiduikit.textfield.bannered

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.FTLBanneredTextViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLBanneredTextViewThemeManager(
    override var darkTheme: FTLBanneredTextViewTheme? =
        FTLBanneredTextViewTheme(
            R.color.TextPrimaryDark,
            R.color.selector_ftl_bannered_text_view_dark
        ),
    override var lightTheme: FTLBanneredTextViewTheme = FTLBanneredTextViewTheme(
        R.color.TextPrimaryLight,
        R.color.selector_ftl_bannered_text_view_light
    )
) : ViewThemeManager<FTLBanneredTextViewTheme>()
