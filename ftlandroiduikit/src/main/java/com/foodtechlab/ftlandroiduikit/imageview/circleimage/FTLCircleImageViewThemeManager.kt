package com.foodtechlab.ftlandroiduikit.imageview.circleimage

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLCircleImageViewDoubleCheckThemeManager(
    override var darkTheme: FTLCircleImageViewTheme? = FTLCircleImageViewTheme(
        R.color.IconBackgroundGreenDark
    ),
    override var lightTheme: FTLCircleImageViewTheme = FTLCircleImageViewTheme(
        R.color.IconBackgroundGreenLight
    )
) : ViewThemeManager<FTLCircleImageViewTheme>()

class FTLCircleImageViewMaintenanceThemeManager(
    override var darkTheme: FTLCircleImageViewTheme? = FTLCircleImageViewTheme(
        R.color.IconBackgroundCyanDark
    ),
    override var lightTheme: FTLCircleImageViewTheme = FTLCircleImageViewTheme(
        R.color.IconBackgroundCyanLight
    )
) : ViewThemeManager<FTLCircleImageViewTheme>()

class FTLCircleImageViewStarThemeManager(
    override var darkTheme: FTLCircleImageViewTheme? = FTLCircleImageViewTheme(
        R.color.IconBackgroundYellowDark
    ),
    override var lightTheme: FTLCircleImageViewTheme = FTLCircleImageViewTheme(
        R.color.IconBackgroundYellowLight
    )

) : ViewThemeManager<FTLCircleImageViewTheme>()

class FTLCircleImageViewMedicalThemeManager(
    override var darkTheme: FTLCircleImageViewTheme? = FTLCircleImageViewTheme(
        R.color.IconPinkDark
    ),
    override var lightTheme: FTLCircleImageViewTheme = FTLCircleImageViewTheme(
        R.color.IconPinkLight
    )
) : ViewThemeManager<FTLCircleImageViewTheme>()

class FTLCircleImageViewQuestionThemeManager(
    override var darkTheme: FTLCircleImageViewTheme? = FTLCircleImageViewTheme(
        R.color.IconBackgroundGreyDark
    ),
    override var lightTheme: FTLCircleImageViewTheme = FTLCircleImageViewTheme(
        R.color.IconBackgroundGreyLight
    )
) : ViewThemeManager<FTLCircleImageViewTheme>()
