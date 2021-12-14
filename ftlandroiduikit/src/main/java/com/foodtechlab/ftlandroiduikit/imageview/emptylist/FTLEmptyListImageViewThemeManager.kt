package com.foodtechlab.ftlandroiduikit.imageview.emptylist

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLEmptyListImageViewThemeManager(
    override var darkTheme: FTLEmptyListImageViewTheme? = FTLEmptyListImageViewTheme(R.drawable.ic_placeholder_empty_order_list_dark),
    override var lightTheme: FTLEmptyListImageViewTheme = FTLEmptyListImageViewTheme(R.drawable.ic_placeholder_empty_order_list_light)
) : ViewThemeManager<FTLEmptyListImageViewTheme>()
