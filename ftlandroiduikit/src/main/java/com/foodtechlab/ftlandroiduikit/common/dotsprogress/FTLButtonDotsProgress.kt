package com.foodtechlab.ftlandroiduikit.common.dotsprogress

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

/**
 * Created by Umalt on 18.06.2020
 */
class FTLButtonDotsProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseDotsProgress(context, attrs, defStyleAttr) {

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        dotColor = ContextCompat.getColor(context, theme.ftlButtonDotsProgressTheme.dotColor)
        bounceDotColor =
            ContextCompat.getColor(context, theme.ftlButtonDotsProgressTheme.bounceDotColor)
    }

    override fun TypedArray.setupDotColor() {
        ThemeManager.Theme.LIGHT.ftlButtonDotsProgressTheme.dotColor = getResourceId(
            R.styleable.FTLDotsProgress_dot_color_light,
            ThemeManager.Theme.LIGHT.ftlButtonDotsProgressTheme.dotColor
        )
        ThemeManager.Theme.DARK.ftlButtonDotsProgressTheme.dotColor = getResourceId(
            R.styleable.FTLDotsProgress_dot_color_dark,
            ThemeManager.Theme.DARK.ftlButtonDotsProgressTheme.dotColor
        )
        dotColor =
            ContextCompat.getColor(context, ThemeManager.theme.ftlButtonDotsProgressTheme.dotColor)
    }

    override fun TypedArray.setupBounceDotColor() {
        ThemeManager.Theme.LIGHT.ftlButtonDotsProgressTheme.bounceDotColor = getResourceId(
            R.styleable.FTLDotsProgress_bounce_dot_color_light,
            ThemeManager.Theme.LIGHT.ftlButtonDotsProgressTheme.bounceDotColor
        )
        ThemeManager.Theme.DARK.ftlButtonDotsProgressTheme.bounceDotColor = getResourceId(
            R.styleable.FTLDotsProgress_bounce_dot_color_dark,
            ThemeManager.Theme.DARK.ftlButtonDotsProgressTheme.bounceDotColor
        )
        bounceDotColor =
            ContextCompat.getColor(
                context,
                ThemeManager.theme.ftlButtonDotsProgressTheme.bounceDotColor
            )
    }
}