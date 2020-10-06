package com.foodtechlab.ftlandroiduikit.button

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FTLFloatingActionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FloatingActionButton(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {

    var fabImageResource = ContextCompat.getDrawable(context, R.drawable.ic_stat_24)
        set(value) {
            field = value
            setImageDrawable(field)
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.FTLFloatingActionButton) {
            fabImageResource = getDrawable(R.styleable.FTLFloatingActionButton_fabImageResource)
                ?: ContextCompat.getDrawable(context, R.drawable.ic_stat_24)
        }
        setRippleColor(null)
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
        backgroundTintList =
            ContextCompat.getColorStateList(context, theme.ftlFloatingActionButtonTheme.bgColor)
    }
}