package com.foodtechlab.ftlandroiduikit.bar

import android.content.Context
import android.util.AttributeSet
import android.view.Menu
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
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

    fun addMenuItems(menuItems: List<FTLMenuItem>) {
        menu.clear()
        menuItems.forEach {
            menu.add(Menu.NONE, it.itemId, Menu.NONE, it.titleRes)
                .setIcon(it.iconRes)
        }
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
        setBackgroundColor(
            ContextCompat.getColor(context, theme.ftlBottomNavigationViewTheme.bgColor)
        )
        itemIconTintList = ContextCompat.getColorStateList(
            context,
            theme.ftlBottomNavigationViewTheme.itemColor
        )
        itemTextColor = ContextCompat.getColorStateList(
            context,
            theme.ftlBottomNavigationViewTheme.itemColor
        )
    }
}

class FTLMenuItem(
    val itemId: Int,
    @StringRes val titleRes: Int,
    @DrawableRes var iconRes: Int
)