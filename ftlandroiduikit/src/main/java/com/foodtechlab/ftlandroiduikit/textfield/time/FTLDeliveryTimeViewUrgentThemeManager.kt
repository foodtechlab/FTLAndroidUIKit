package com.foodtechlab.ftlandroiduikit.textfield.time

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLDeliveryTimeViewUrgentThemeManager(
    override var darkTheme: FTLDeliveryTimeViewTheme? = FTLDeliveryTimeViewTheme(
        R.color.TextOnColorPrimaryDark,
        R.color.TimeUrgentDark,
        R.color.IconPrimaryDark
    ), // urgent
    override var lightTheme: FTLDeliveryTimeViewTheme =
        FTLDeliveryTimeViewTheme(
            R.color.TextOnColorPrimaryLight,
            R.color.TimeUrgentLight,
            R.color.IconPrimaryLight
        ), // urgent
) : ViewThemeManager<FTLDeliveryTimeViewTheme>()