package com.foodtechlab.ftlandroiduikit.button.image

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLImageButtonThemeManager(
    override var darkTheme: FTLImageButtonTheme? = FTLImageButtonTheme(
        R.drawable.selector_additional_button_navigator_small_dark,
        R.drawable.selector_additional_button_navigator_medium_dark,
        R.drawable.selector_additional_button_navigator_large_dark,
        R.drawable.selector_additional_button_location_small_dark,
        R.drawable.selector_additional_button_location_medium_dark,
        R.drawable.selector_additional_button_location_large_dark,
        R.drawable.selector_additional_button_info_small_dark,
        R.drawable.selector_additional_button_info_medium_dark,
        R.drawable.selector_additional_button_info_large_dark,
        R.drawable.selector_additional_button_replay_small_dark,
        R.drawable.selector_additional_button_replay_medium_dark,
        R.drawable.selector_additional_button_replay_large_dark,
        R.drawable.selector_additional_button_trash_large_dark,
        R.drawable.selector_additional_button_record_large_dark,
        R.drawable.selector_additional_button_stop_large_dark,
        R.drawable.selector_additional_button_play_medium_dark,
        R.drawable.selector_additional_button_play_large_dark,
        R.drawable.selector_additional_button_pause_medium_dark,
        R.drawable.selector_additional_button_pause_large_dark
    ),
    override var lightTheme: FTLImageButtonTheme = FTLImageButtonTheme(
        R.drawable.selector_additional_button_navigator_small_light,
        R.drawable.selector_additional_button_navigator_medium_light,
        R.drawable.selector_additional_button_navigator_large_light,
        R.drawable.selector_additional_button_location_small_light,
        R.drawable.selector_additional_button_location_medium_light,
        R.drawable.selector_additional_button_location_large_light,
        R.drawable.selector_additional_button_info_small_light,
        R.drawable.selector_additional_button_info_medium_light,
        R.drawable.selector_additional_button_info_large_light,
        R.drawable.selector_additional_button_replay_small_light,
        R.drawable.selector_additional_button_replay_medium_light,
        R.drawable.selector_additional_button_replay_large_light,
        R.drawable.selector_additional_button_trash_large_light,
        R.drawable.selector_additional_button_record_large_light,
        R.drawable.selector_additional_button_stop_large_light,
        R.drawable.selector_additional_button_play_medium_light,
        R.drawable.selector_additional_button_play_large_light,
        R.drawable.selector_additional_button_pause_medium_light,
        R.drawable.selector_additional_button_pause_large_light
    ),
) : ViewThemeManager<FTLImageButtonTheme>()
