package com.foodtechlab.ftlandroiduikit.container

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FTLLinearLayoutThemeManager : ViewThemeManager<FTLLinearLayoutTheme>() {
    init {
        darkTheme = FTLLinearLayoutTheme(R.color.BackgroundDefaultDark)
        lightTheme = FTLLinearLayoutTheme(R.color.BackgroundDefaultLight)
    }

    override fun mapToViewData(): Flow<FTLLinearLayoutTheme?> {
        return ThemeManager.stateTheme.map {
            when (it) {
                ThemeManager.Theme.DARK -> {
                    darkTheme
                }
                else -> {
                    lightTheme
                }
            }
        }
    }
}
