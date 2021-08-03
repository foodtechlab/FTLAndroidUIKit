package com.foodtechlab.ftlandroiduikit.progress

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlin.math.roundToInt

class FTLLinearProgressIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {
    var isIndeterminate = false
        set(value) {
            field = value
            pbIndicator.isIndeterminate = field
        }

    var currentProgress: Int = 0
        set(value) {
            field = value
            pbIndicator.progress = field
        }

    var maxProgress: Int = 100
        set(value) {
            field = value
            pbIndicator.max = field
        }

    var trackCornerRadius: Int = 0
        get() = pbIndicator.trackCornerRadius
        set(value) {
            field = value
            pbIndicator.trackCornerRadius = field
        }

    var trackThickness: Int = 0
        get() = pbIndicator.trackThickness
        set(value) {
            field = value
            pbIndicator.trackThickness = field
        }

    @ColorRes
    private var trackColor = -1

    @ColorRes
    private var indicatorColor = -1

    private var pbIndicator: LinearProgressIndicator

    init {
        inflate(context, R.layout.layout_ftl_linear_progress_indicator, this)
        pbIndicator = findViewById(R.id.lpi_linear_progress_indicator)

        context.withStyledAttributes(attrs, R.styleable.FTLLinearProgressIndicator) {
            trackThickness = getDimension(
                R.styleable.FTLLinearProgressIndicator_trackThickness,
                context.dpToPx(DEFAULT_TRACK_THICKNESS)
            ).roundToInt()

            trackCornerRadius = getDimension(
                R.styleable.FTLLinearProgressIndicator_trackCornerRadius,
                0F
            ).roundToInt()
            isIndeterminate = getBoolean(
                R.styleable.FTLLinearProgressIndicator_android_indeterminate,
                false
            )
            maxProgress = getInteger(R.styleable.FTLLinearProgressIndicator_android_max, 100)
            currentProgress = getInteger(R.styleable.FTLLinearProgressIndicator_android_progress, 0)
        }

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
        updateTrackColorTheme(trackColor)
        updateIndicatorColorTheme(indicatorColor)
    }

    fun updateTrackColorTheme(@ColorRes colorRes: Int) {
        trackColor = colorRes
        pbIndicator.trackColor = ContextCompat.getColor(
            context,
            if (trackColor != -1) trackColor
            else ThemeManager.theme.ftlLinearProgressIndicatorTheme.trackColor
        )
    }

    fun updateIndicatorColorTheme(@ColorRes colorRes: Int) {
        trackColor = colorRes
        pbIndicator.setIndicatorColor(
            ContextCompat.getColor(
                context,
                if (trackColor != -1) trackColor
                else ThemeManager.theme.ftlLinearProgressIndicatorTheme.indicatorColor
            )
        )
    }

    companion object {
        private const val DEFAULT_TRACK_THICKNESS = 4F
    }
}