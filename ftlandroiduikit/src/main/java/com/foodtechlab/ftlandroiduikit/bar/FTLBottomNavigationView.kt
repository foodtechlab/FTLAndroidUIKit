package com.foodtechlab.ftlandroiduikit.bar

import android.content.Context
import android.util.AttributeSet
import android.view.Menu
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode

/**
 * Created by Umalt on 22.05.2020
 */
class FTLBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.bottomNavigationStyle
) : BottomNavigationView(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {

    init {
        itemIconTintList = null
        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED

        onThemeChanged(ThemeManager.theme)
    }

    fun addMenuItems(menuItems: List<MenuItem>) {
        menu.clear()
        menuItems.forEach {
            menu.add(Menu.NONE, it.itemId, Menu.NONE, it.titleRes)
                .setIcon(it.iconRes)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ThemeManager.addListener(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
    }

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        MenuItem.ORDERS.iconRes = theme.ftlBottomNavigationViewTheme.itemOrdersIcon
        MenuItem.MAPS.iconRes = theme.ftlBottomNavigationViewTheme.itemMapsIcon
        MenuItem.HISTORY.iconRes = theme.ftlBottomNavigationViewTheme.itemHistoryIcon
        MenuItem.MORE.iconRes = theme.ftlBottomNavigationViewTheme.itemMoreIcon

        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            if (item.itemId == MenuItem.values()[i].itemId) {
                item.setIcon(MenuItem.values()[i].iconRes)
            }
        }

        setBackgroundColor(
            ContextCompat.getColor(context, theme.ftlBottomNavigationViewTheme.bgColor)
        )

        itemTextColor = ContextCompat.getColorStateList(
            context,
            theme.ftlBottomNavigationViewTheme.itemTextColor
        )
    }

    enum class MenuItem(
        val itemId: Int,
        @StringRes val titleRes: Int,
        @DrawableRes var iconRes: Int
    ) {
        ORDERS(
            ID_ORDERS,
            R.string.ftl_bnv_orders,
            ThemeManager.theme.ftlBottomNavigationViewTheme.itemOrdersIcon
        ),
        MAPS(
            ID_MAPS,
            R.string.ftl_bnv_maps,
            ThemeManager.theme.ftlBottomNavigationViewTheme.itemMapsIcon
        ),
        HISTORY(
            ID_HISTORY,
            R.string.ftl_bnv_history,
            ThemeManager.theme.ftlBottomNavigationViewTheme.itemHistoryIcon
        ),
        MORE(
            ID_MORE,
            R.string.ftl_bnv_more,
            ThemeManager.theme.ftlBottomNavigationViewTheme.itemMoreIcon
        )
    }

    companion object {
        const val ID_ORDERS = 2314
        const val ID_MAPS = 5634
        const val ID_HISTORY = 2378
        const val ID_MORE = 2745
    }
}