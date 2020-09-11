package com.foodtechlab.ftlandroiduikitsample.components

import com.foodtechlab.ftlandroiduikitsample.R
import com.foodtechlab.ftlandroiduikitsample.base.ItemBase

const val TYPE_POSITION_ITEM = R.layout.rv_position_item
const val TYPE_PAYMENT_ITEM = R.layout.rv_payment_item

data class ItemPosition(
    val title: String,
    val count: Int,
    val price: Double,
    override val itemViewType: Int = TYPE_POSITION_ITEM
) : ItemBase {

    override fun getItemId() = hashCode()
}

data class ItemPayment(
    val type: PaymentType,
    val name: String,
    val amount: Double,
    override val itemViewType: Int = TYPE_PAYMENT_ITEM
) : ItemBase {

    override fun getItemId() = hashCode()
}

enum class PaymentType {
    MONEY,
    ONLINE
}