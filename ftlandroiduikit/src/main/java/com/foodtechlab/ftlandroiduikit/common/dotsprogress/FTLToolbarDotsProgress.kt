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
class FTLToolbarDotsProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseDotsProgress(context, attrs, defStyleAttr) {

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        dotColor = ContextCompat.getColor(context, theme.ftlToolbarDotsProgressTheme.dotColor)
        bounceDotColor =
            ContextCompat.getColor(context, theme.ftlToolbarDotsProgressTheme.bounceDotColor)
    }

    override fun TypedArray.setupDotColor() {
        ThemeManager.Theme.LIGHT.ftlToolbarDotsProgressTheme.dotColor = getResourceId(
            R.styleable.FTLDotsProgress_dot_color_light,
            ThemeManager.Theme.LIGHT.ftlToolbarDotsProgressTheme.dotColor
        )
        ThemeManager.Theme.DARK.ftlToolbarDotsProgressTheme.dotColor = getResourceId(
            R.styleable.FTLDotsProgress_dot_color_dark,
            ThemeManager.Theme.DARK.ftlToolbarDotsProgressTheme.dotColor
        )
        dotColor =
            ContextCompat.getColor(context, ThemeManager.theme.ftlToolbarDotsProgressTheme.dotColor)
    }

    override fun TypedArray.setupBounceDotColor() {
        ThemeManager.Theme.LIGHT.ftlToolbarDotsProgressTheme.bounceDotColor = getResourceId(
            R.styleable.FTLDotsProgress_bounce_dot_color_light,
            ThemeManager.Theme.LIGHT.ftlToolbarDotsProgressTheme.bounceDotColor
        )
        ThemeManager.Theme.DARK.ftlToolbarDotsProgressTheme.bounceDotColor = getResourceId(
            R.styleable.FTLDotsProgress_bounce_dot_color_dark,
            ThemeManager.Theme.DARK.ftlToolbarDotsProgressTheme.bounceDotColor
        )
        bounceDotColor =
            ContextCompat.getColor(
                context,
                ThemeManager.theme.ftlToolbarDotsProgressTheme.bounceDotColor
            )
    }
}