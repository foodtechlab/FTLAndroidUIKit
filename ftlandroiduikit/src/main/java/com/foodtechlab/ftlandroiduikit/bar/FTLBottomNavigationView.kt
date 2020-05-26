package com.foodtechlab.ftlandroiduikit.bar

import android.content.Context
import android.util.AttributeSet
import android.view.Menu
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode

/**
 * Created by Umalt on 22.05.2020
 */
class FTLBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.bottomNavigationStyle
) : BottomNavigationView(context, attrs, defStyleAttr) {

    init {
        setBackgroundColor(ContextCompat.getColor(context, R.color.OnPrimary))

        minimumHeight = context.dpToPx(MIN_HEIGHT).toInt()
        itemIconTintList = null
        itemTextColor = ContextCompat.getColorStateList(
            context,
            R.color.selector_ftl_bnv_item_text_color
        )
        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED

        menu.add(Menu.NONE, ITEM_ID_ORDERS, Menu.NONE, context.getString(R.string.ftl_bnv_orders))
            .setIcon(R.drawable.selector_ftl_bnv_orders)
        menu.add(Menu.NONE, ITEM_ID_MAPS, Menu.NONE, context.getString(R.string.ftl_bnv_maps))
            .setIcon(R.drawable.selector_ftl_bnv_maps)
        menu.add(Menu.NONE, ITEM_ID_HISTORY, Menu.NONE, context.getString(R.string.ftl_bnv_history))
            .setIcon(R.drawable.selector_ftl_bnv_histories)
        menu.add(Menu.NONE, ITEM_ID_MORE, Menu.NONE, context.getString(R.string.ftl_bnv_more))
            .setIcon(R.drawable.selector_ftl_bnv_more)
    }

    companion object {
        private const val MIN_HEIGHT = 56f

        const val ITEM_ID_ORDERS = 2314
        const val ITEM_ID_MAPS = 5634
        const val ITEM_ID_HISTORY = 2378
        const val ITEM_ID_MORE = 2745
    }
}