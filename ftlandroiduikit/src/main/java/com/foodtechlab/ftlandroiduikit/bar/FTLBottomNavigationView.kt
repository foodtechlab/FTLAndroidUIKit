package com.foodtechlab.ftlandroiduikit.bar

import android.content.Context
import android.util.AttributeSet
import android.view.Menu
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by Umalt on 22.05.2020
 */
class FTLBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.bottomNavigationStyle
) : BottomNavigationView(context, attrs, defStyleAttr), CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLBottomNavigationViewTheme> =
        FTLBottomNavigationViewThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    init {
        itemIconTintList = null
        labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
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
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                onThemeChanged(theme)
            }
        }
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    private fun onThemeChanged(theme: FTLBottomNavigationViewTheme) {
        setBackgroundColor(
            ContextCompat.getColor(context, theme.bgColor)
        )
        itemIconTintList = ContextCompat.getColorStateList(
            context,
            theme.itemColor
        )
        itemTextColor = ContextCompat.getColorStateList(
            context,
            theme.itemColor
        )
    }
}

class FTLMenuItem(
    val itemId: Int,
    @StringRes val titleRes: Int,
    @DrawableRes var iconRes: Int
)

data class FTLBottomNavigationViewTheme(
    @ColorRes val bgColor: Int,
    @ColorRes val itemColor: Int
) : ViewTheme()
