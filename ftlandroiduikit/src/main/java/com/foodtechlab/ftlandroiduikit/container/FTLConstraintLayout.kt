package com.foodtechlab.ftlandroiduikit.container

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.util.ThemeManager


class FTLConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defAttrStyle: Int = 0
) : ConstraintLayout(context, attrs, defAttrStyle), ThemeManager.ThemeChangedListener {

    init {
        onThemeChanged(ThemeManager.theme)
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
                theme.ftlConstraintLayoutTheme.bgColor
            )
        )
    }

    fun updateBackgroundColor(@ColorRes lightColor: Int, @ColorRes darkColor: Int) {
        ThemeManager.Theme.LIGHT.ftlConstraintLayoutTheme.bgColor = lightColor
        ThemeManager.Theme.DARK.ftlConstraintLayoutTheme.bgColor = darkColor
        setBackgroundColor(
            ContextCompat.getColor(
                context,
                ThemeManager.theme.ftlConstraintLayoutTheme.bgColor
            )
        )
    }
}