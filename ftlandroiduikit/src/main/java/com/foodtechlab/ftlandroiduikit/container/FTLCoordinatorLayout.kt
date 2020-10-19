package com.foodtechlab.ftlandroiduikit.container

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.util.ThemeManager


class FTLCoordinatorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defAttrStyle: Int = 0
) : CoordinatorLayout(context, attrs, defAttrStyle), ThemeManager.ThemeChangedListener {

    init {
        onThemeChanged(ThemeManager.theme)
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
        setBackgroundColor(
            ContextCompat.getColor(
                context,
                theme.ftlCoordinatorLayoutTheme.bgColor
            )
        )
    }

    fun updateBackgroundColor(@ColorRes lightColor: Int, @ColorRes darkColor: Int) {
        ThemeManager.Theme.LIGHT.ftlCoordinatorLayoutTheme.bgColor = lightColor
        ThemeManager.Theme.DARK.ftlCoordinatorLayoutTheme.bgColor = darkColor
        setBackgroundColor(
            ContextCompat.getColor(
                context,
                ThemeManager.theme.ftlCoordinatorLayoutTheme.bgColor
            )
        )
    }
}