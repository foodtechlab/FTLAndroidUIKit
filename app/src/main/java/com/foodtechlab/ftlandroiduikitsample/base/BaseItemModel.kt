package com.foodtechlab.ftlandroiduikitsample.base

import androidx.annotation.IntDef
import com.foodtechlab.ftlandroiduikitsample.main.ITEM_TIMER

/**
 * Created by Umalt on 04.08.2020
 */
@IntDef(
    ITEM_TIMER
)
@Retention(AnnotationRetention.SOURCE)
annotation class ItemViewType

interface ItemBase : DiffComparable {

    @ItemViewType
    val itemViewType: Int
}