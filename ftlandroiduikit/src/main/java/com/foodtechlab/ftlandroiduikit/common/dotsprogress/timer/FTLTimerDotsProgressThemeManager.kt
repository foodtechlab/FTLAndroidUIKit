package com.foodtechlab.ftlandroiduikit.common.dotsprogress.timer

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.dotsprogress.FTLDotsProgressTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLTimerDotsProgressThemeManager(
    override var darkTheme: FTLDotsProgressTheme? = FTLDotsProgressTheme(
        R.color.IconGreyLightOpacity60,
        R.color.IconGreyLightOpacity80
    ),
    override var lightTheme: FTLDotsProgressTheme = FTLDotsProgressTheme(
        R.color.IconGreyLightOpacity60,
        R.color.IconGreyLightOpacity80
    )
) : ViewThemeManager<FTLDotsProgressTheme>()
