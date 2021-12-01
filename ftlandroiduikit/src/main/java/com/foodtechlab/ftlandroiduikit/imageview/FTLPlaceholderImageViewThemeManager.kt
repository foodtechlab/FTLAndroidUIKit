package com.foodtechlab.ftlandroiduikit.imageview

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FTLPlaceholderImageViewThemeManager : ViewThemeManager<FTLPlaceholderImageViewTheme>() {
    init {
        lightTheme = FTLPlaceholderImageViewTheme(R.drawable.ic_restaurant_placeholder_light)
        darkTheme = FTLPlaceholderImageViewTheme(R.drawable.ic_restaurant_placeholder_dark)
    }

    override fun mapToViewData(): Flow<FTLPlaceholderImageViewTheme?> {
        return ThemeManager.stateTheme.map {
            when (it) {
                ThemeManager.Theme.DARK -> darkTheme
                else -> lightTheme
            }
        }
    }
}
