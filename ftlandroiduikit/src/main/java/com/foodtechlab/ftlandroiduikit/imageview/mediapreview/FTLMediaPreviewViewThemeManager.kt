package com.foodtechlab.ftlandroiduikit.imageview.mediapreview

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLMediaPreviewViewThemeManager(
    override var darkTheme: FTLMediaPreviewViewTheme? = FTLMediaPreviewViewTheme(
        R.color.ProgressPrimaryDark,
        R.color.ButtonSecondaryEnableDark,
        R.color.TextOnColorAdditionalDark
    ),
    override var lightTheme: FTLMediaPreviewViewTheme = FTLMediaPreviewViewTheme(
        R.color.ProgressPrimaryLight,
        R.color.ButtonSecondaryEnableLight,
        R.color.TextSecondaryLight
    )
) : ViewThemeManager<FTLMediaPreviewViewTheme>()
