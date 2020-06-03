package com.foodtechlab.ftlandroiduikit.button.additional

import androidx.annotation.DrawableRes
import com.foodtechlab.ftlandroiduikit.R

enum class AdditionalButtonType(@DrawableRes val bgRes: Int? = null) {
    TEXT,

    NAVIGATOR_SMALL(R.drawable.selector_additional_button_navigator_small),
    NAVIGATOR_MEDIUM(R.drawable.selector_additional_button_navigator_medium),
    NAVIGATOR_LARGE(R.drawable.selector_additional_button_navigator_large),

    LOCATION_SMALL(R.drawable.selector_additional_button_location_small),
    LOCATION_MEDIUM(R.drawable.selector_additional_button_location_medium),
    LOCATION_LARGE(R.drawable.selector_additional_button_location_large),

    INFO_SMALL(R.drawable.selector_additional_button_info_small),
    INFO_MEDIUM(R.drawable.selector_additional_button_info_medium),
    INFO_LARGE(R.drawable.selector_additional_button_info_large)
}