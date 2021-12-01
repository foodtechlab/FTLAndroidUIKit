package com.foodtechlab.ftlandroiduikit.card

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FTLCardViewThemeManager : ViewThemeManager<FTLCardViewTheme>() {
    override fun mapToViewData(): Flow<FTLCardViewTheme?> {
        return ThemeManager.stateTheme.map {
            when (it) {
                ThemeManager.Theme.DARK -> darkTheme
                else -> lightTheme
            }
        }
    }

    init {
        lightTheme = FTLCardViewTheme(R.color.SurfaceFirstLight)
        darkTheme = FTLCardViewTheme(R.color.SurfaceFirstDark)
    }
}