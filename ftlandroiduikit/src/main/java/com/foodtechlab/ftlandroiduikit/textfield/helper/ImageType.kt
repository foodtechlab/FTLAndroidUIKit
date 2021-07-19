package com.foodtechlab.ftlandroiduikit.textfield.helper

import androidx.annotation.DrawableRes
import com.foodtechlab.ftlandroiduikit.R

enum class ImageType(@DrawableRes val imgRes: Int) {
    CASH(R.drawable.ic_money),
    CARD_COURIER(R.drawable.ic_terminal),
    CARD_ONLINE(R.drawable.ic_credit_card),
    CUSTOMER(R.drawable.ic_person),
    DELIVERY_ADDRESS(R.drawable.ic_delivery_address),
    ORDER_SOURCE(R.drawable.ic_order_source),
    COMMENT(R.drawable.ic_comment),
    FAST_FOOD(R.drawable.ic_fast_food),
    BONUSES(R.drawable.ic_bonuses),
    FILE(R.drawable.ic_file),
    SHIELD(R.drawable.ic_shield),
    INFO(R.drawable.ic_data),
    PLANE(R.drawable.ic_plane),
    SETTINGS(R.drawable.ic_settings),
    NOTIFICATIONS(R.drawable.ic_notification),
    NAVIGATOR(R.drawable.ic_navigation),
    THEME(R.drawable.ic_theme),
    ALL_PAID(R.drawable.ic_double_check),
    NOT_ALL_PAID(R.drawable.ic_warning_info),
    MORE_DETAILS(R.drawable.ic_more_details),
    DOCS(R.drawable.ic_docs),
    NONE(-1),
    PURSE(R.drawable.ic_purse),
    KEY(R.drawable.ic_key),
    LOCATION(R.drawable.ic_location_marker),
    PHONE(R.drawable.ic_telephone),
    MAP_PIN(R.drawable.ic_map_pin),
    PIN_CODE(R.drawable.ic_pin_code),
    RESTAURANT(R.drawable.ic_restaurant),
    CHECKLIST(R.drawable.ic_checklist)
}