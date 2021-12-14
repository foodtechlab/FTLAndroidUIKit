package com.foodtechlab.ftlandroiduikit.snackbar.top

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLSnackbarThemeManager(
    override var darkTheme: FTLSnackbarTheme? =
        FTLSnackbarTheme(R.drawable.layer_list_snackbar_light),
    override var lightTheme: FTLSnackbarTheme = FTLSnackbarTheme(R.drawable.layer_list_snackbar_dark)
) : ViewThemeManager<FTLSnackbarTheme>()
