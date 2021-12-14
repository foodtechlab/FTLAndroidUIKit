package com.foodtechlab.ftlandroiduikit.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class ViewThemeManager<VD : ViewTheme> {
    abstract var darkTheme: VD?
    abstract var lightTheme: VD
    fun mapToViewData(): Flow<VD> {
        return ThemeManager.stateTheme.map {
            when (it) {
                ThemeManager.Theme.DARK -> darkTheme ?: lightTheme
                else -> lightTheme
            }
        }
    }
}
