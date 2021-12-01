package com.foodtechlab.ftlandroiduikit.container

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FTLConstraintLayoutThemeManager : ViewThemeManager<FTLConstraintLayoutTheme>() {
    init {
        darkTheme =
            FTLConstraintLayoutTheme(R.color.BackgroundDefaultDark)
        lightTheme =
            FTLConstraintLayoutTheme(R.color.BackgroundDefaultLight)
    }

    override fun mapToViewData(): Flow<FTLConstraintLayoutTheme?> {
        return ThemeManager.stateTheme.map {
            when (it) {
                ThemeManager.Theme.DARK -> darkTheme
                else -> lightTheme
            }
        }
    }
}
