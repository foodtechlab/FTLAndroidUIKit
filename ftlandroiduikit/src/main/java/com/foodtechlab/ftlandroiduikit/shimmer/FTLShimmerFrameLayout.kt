package com.foodtechlab.ftlandroiduikit.shimmer

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by Umalt on 06.10.2020
 */
class FTLShimmerFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ShimmerFrameLayout(context, attrs, defStyleAttr),
    CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLShimmerFrameLayoutTheme> =
        FTLShimmerFrameLayoutThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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

    fun onThemeChanged(theme: FTLShimmerFrameLayoutTheme) {
        setShimmer(
            Shimmer.ColorHighlightBuilder()
                .setBaseColor(
                    ContextCompat.getColor(context, theme.baseColor)
                )
                .setHighlightColor(
                    ContextCompat.getColor(
                        context,
                        theme.highlightingColor
                    )
                )
                .setBaseAlpha(1f)
                .setHighlightAlpha(1f)
                .build()
        )
    }
}

data class FTLShimmerFrameLayoutTheme(
    @ColorRes val baseColor: Int,
    @ColorRes val highlightingColor: Int
) : ViewTheme()
