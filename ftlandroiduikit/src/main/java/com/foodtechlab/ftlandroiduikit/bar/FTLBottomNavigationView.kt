package com.foodtechlab.ftlandroiduikit.bar

import android.content.Context
import android.util.AttributeSet
import android.view.Menu
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
    }

    fun addMenuItems(menuItems: List<MenuItem>) {
        menu.clear()
        menuItems.forEach {
            menu.add(Menu.NONE, it.itemId, Menu.NONE, it.titleRes)
                .setIcon(it.iconRes)
        }
    }

    enum class MenuItem(
        val itemId: Int,
        @StringRes val titleRes: Int,
        @DrawableRes val iconRes: Int
    ) {
        ORDERS(ID_ORDERS, R.string.ftl_bnv_orders, R.drawable.selector_ftl_bnv_orders),
        MAPS(ID_MAPS, R.string.ftl_bnv_maps, R.drawable.selector_ftl_bnv_maps),
        HISTORY(ID_HISTORY, R.string.ftl_bnv_history, R.drawable.selector_ftl_bnv_history),
        MORE(ID_MORE, R.string.ftl_bnv_more, R.drawable.selector_ftl_bnv_more)
    }

    companion object {
        private const val MIN_HEIGHT = 56f

        const val ID_ORDERS = 2314
        const val ID_MAPS = 5634
        const val ID_HISTORY = 2378
        const val ID_MORE = 2745
    }
}