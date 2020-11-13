package com.foodtechlab.ftlandroiduikit.shimmer

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

/**
 * Created by Umalt on 06.10.2020
 */
class FTLShimmerFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ShimmerFrameLayout(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {

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
        setShimmer(
            Shimmer.ColorHighlightBuilder()
                .setBaseColor(
                    ContextCompat.getColor(context, theme.ftlShimmerFrameLayoutTheme.baseColor)
                )
                .setHighlightColor(
                    ContextCompat.getColor(
                        context,
                        theme.ftlShimmerFrameLayoutTheme.highlightingColor
                    )
                )
                .setBaseAlpha(1f)
                .setHighlightAlpha(1f)
                .build()
        )
    }
}