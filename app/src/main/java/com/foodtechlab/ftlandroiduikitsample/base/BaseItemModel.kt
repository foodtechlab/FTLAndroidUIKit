package com.foodtechlab.ftlandroiduikitsample.base

import androidx.annotation.IntDef
import com.foodtechlab.ftlandroiduikitsample.components.TYPE_PAYMENT_ITEM
import com.foodtechlab.ftlandroiduikitsample.components.TYPE_POSITION_ITEM

/**
 * Created by Umalt on 04.08.2020
 */
@IntDef(
    TYPE_POSITION_ITEM,
    TYPE_PAYMENT_ITEM
)
@Retention(AnnotationRetention.SOURCE)
annotation class ItemViewType

interface ItemBase : DiffComparable {

    @ItemViewType
    val itemViewType: Int
}