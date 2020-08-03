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
    USER_PLACEHOLDER(R.drawable.ic_user_placeholder),
    PLANE(R.drawable.ic_plane),
    SETTINGS(R.drawable.ic_settings),
    NOTIFICATIONS(R.drawable.ic_notification),
    NAVIGATOR(R.drawable.ic_navigation),
    THEME(R.drawable.ic_theme),
    UNKNOWN_PAYMENT(R.drawable.ic_unknown_dots),
    ALL_PAID(R.drawable.ic_double_check),
    NOT_ALL_PAID(R.drawable.ic_warning_info),
    MORE_DETAILS(R.drawable.ic_more_details),
    DOCS(R.drawable.ic_docs),
    NONE(-1)
}