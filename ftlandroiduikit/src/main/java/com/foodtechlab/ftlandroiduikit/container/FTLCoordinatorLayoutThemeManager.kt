package com.foodtechlab.ftlandroiduikit.container

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FTLCoordinatorLayoutThemeManager : ViewThemeManager<FTLCoordinatorLayoutTheme>() {
    init {
        darkTheme = FTLCoordinatorLayoutTheme(R.color.BackgroundSecondaryDark)
        lightTheme = FTLCoordinatorLayoutTheme(R.color.BackgroundSecondaryLight)
    }

    override fun mapToViewData(): Flow<FTLCoordinatorLayoutTheme?> {
        return ThemeManager.stateTheme.map {
            when (it) {
                ThemeManager.Theme.DARK -> darkTheme
                else -> lightTheme
            }
        }
    }
}
