package com.foodtechlab.ftlandroiduikit.button.additional

import androidx.annotation.DrawableRes
import com.foodtechlab.ftlandroiduikit.R

enum class AdditionalButtonType(@DrawableRes val bgRes: Int? = null, val size: Int = -1) {
    TEXT,

    NAVIGATOR_SMALL(R.drawable.selector_additional_button_navigator_small, 32),
    NAVIGATOR_MEDIUM(R.drawable.selector_additional_button_navigator_medium, 40),
    NAVIGATOR_LARGE(R.drawable.selector_additional_button_navigator_large, 48),

    LOCATION_SMALL(R.drawable.selector_additional_button_location_small, 32),
    LOCATION_MEDIUM(R.drawable.selector_additional_button_location_medium, 40),
    LOCATION_LARGE(R.drawable.selector_additional_button_location_large, 48),

    INFO_SMALL(R.drawable.selector_additional_button_info_small, 32),
    INFO_MEDIUM(R.drawable.selector_additional_button_info_medium, 40),
    INFO_LARGE(R.drawable.selector_additional_button_info_large, 48)
}