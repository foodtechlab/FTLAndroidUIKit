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
    COMMENT(R.drawable.ic_comment)
}