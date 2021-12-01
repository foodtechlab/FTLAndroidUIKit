package com.foodtechlab.ftlandroiduikit.util

import kotlinx.coroutines.flow.Flow

abstract class ViewThemeManager<VD : ViewTheme> {
    var darkTheme: VD? = null
    var lightTheme: VD? = null
    abstract fun mapToViewData(): Flow<VD?>
}
