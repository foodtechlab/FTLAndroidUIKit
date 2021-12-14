package com.foodtechlab.ftlandroiduikit.map.restaurant.marker

import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager

class FTLRestaurantMarkerThemeManager(
    override var darkTheme: FTLRestaurantMarkerTheme? = FTLRestaurantMarkerTheme(
        R.drawable.shape_ftl_restaurant_marker_dark,
        R.color.TextPrimaryDark
    ),
    override var lightTheme: FTLRestaurantMarkerTheme =
        FTLRestaurantMarkerTheme(
            R.drawable.shape_ftl_restaurant_marker_light,
            R.color.TextPrimaryLight
        )
) : ViewThemeManager<FTLRestaurantMarkerTheme>()