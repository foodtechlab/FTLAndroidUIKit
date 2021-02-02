package com.foodtechlab.ftlandroiduikit.common.dotsprogress

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

/**
 * Created by Umalt on 09.10.2020
 */
class FTLTimerDotsProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defAttrStyle: Int = 0
) : BaseDotsProgress(context, attrs, defAttrStyle) {

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        dotColor = ContextCompat.getColor(context, theme.ftlTimerDotsProgressTheme.dotColor)
        bounceDotColor =
            ContextCompat.getColor(context, theme.ftlTimerDotsProgressTheme.bounceDotColor)
    }

    override fun TypedArray.setupDotColor() {
        ThemeManager.Theme.LIGHT.ftlTimerDotsProgressTheme.dotColor = getResourceId(
            R.styleable.FTLDotsProgress_dot_color_light,
            ThemeManager.Theme.LIGHT.ftlTimerDotsProgressTheme.dotColor
        )
        ThemeManager.Theme.DARK.ftlTimerDotsProgressTheme.dotColor = getResourceId(
            R.styleable.FTLDotsProgress_dot_color_dark,
            ThemeManager.Theme.DARK.ftlTimerDotsProgressTheme.dotColor
        )
        dotColor =
            ContextCompat.getColor(context, ThemeManager.theme.ftlTimerDotsProgressTheme.dotColor)
    }

    override fun TypedArray.setupBounceDotColor() {
        ThemeManager.Theme.LIGHT.ftlTimerDotsProgressTheme.bounceDotColor = getResourceId(
            R.styleable.FTLDotsProgress_bounce_dot_color_light,
            ThemeManager.Theme.LIGHT.ftlTimerDotsProgressTheme.bounceDotColor
        )
        ThemeManager.Theme.DARK.ftlTimerDotsProgressTheme.bounceDotColor = getResourceId(
            R.styleable.FTLDotsProgress_bounce_dot_color_dark,
            ThemeManager.Theme.DARK.ftlTimerDotsProgressTheme.bounceDotColor
        )
        bounceDotColor =
            ContextCompat.getColor(
                context,
                ThemeManager.theme.ftlTimerDotsProgressTheme.bounceDotColor
            )
    }
}