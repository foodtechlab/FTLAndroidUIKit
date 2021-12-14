package com.foodtechlab.ftlandroiduikit.common.dotsprogress.button

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.dotsprogress.FTLDotsProgressTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLButtonDotsProgressThemeManager(
    override var darkTheme: FTLDotsProgressTheme? = FTLDotsProgressTheme(
        R.color.TextOnColorPrimaryOpacity60Dark,
        R.color.TextOnColorPrimaryDark
    ),
    override var lightTheme: FTLDotsProgressTheme = FTLDotsProgressTheme(
        R.color.TextOnColorPrimaryOpacity60Light,
        R.color.TextOnColorPrimaryLight
    )
) : ViewThemeManager<FTLDotsProgressTheme>()