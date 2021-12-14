package com.foodtechlab.ftlandroiduikit.switch

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.drawable.DrawableCompat
import com.facebook.shimmer.Shimmer
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.shimmer.FTLShimmerFrameLayoutTheme
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FTLSwitch : SwitchCompat, CoroutineScope {
    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    @ColorInt
    private var thumbOnColor = ContextCompat.getColor(context, R.color.ButtonDangerEnableLight)

    @ColorInt
    private var thumbOffColor = ContextCompat.getColor(context, R.color.ButtonSecondaryEnableLight)

    @ColorInt
    private var trackOnColor =
        ContextCompat.getColor(context, R.color.SwitchTrackProgressLight)

    @ColorInt
    private var trackOffColor =
        ContextCompat.getColor(context, R.color.SwitchTrackEnableLight)

    @ColorInt
    private var highlightOnColor =
        ContextCompat.getColor(context, R.color.SwitchTrackProgressLight)

    @ColorInt
    private var highlightOffColor =
        ContextCompat.getColor(context, R.color.SwitchTrackEnableLight)

    private val states = arrayOf(
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_checked)
    )

    private val viewThemeManager: ViewThemeManager<FTLSwitchTheme> = FTLSwitchThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private fun init(attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.FTLSwitch) {
            thumbOnColor = getColor(
                R.styleable.FTLSwitch_thumbAccentColor,
                ContextCompat.getColor(context, R.color.ButtonDangerEnableLight)
            )

            trackOnColor = getColor(
                R.styleable.FTLSwitch_trackAccentColor,
                ContextCompat.getColor(context, R.color.SwitchTrackProgressLight)
            )

            highlightOnColor = getColor(
                R.styleable.FTLSwitch_highlightAccentColor,
                ContextCompat.getColor(context, R.color.SwitchTrackProgressLight)
            )
        }

        textSize = 16f
        setLineSpacing(context.dpToPx(5f), 1.0f)

        if (!isInEditMode) {
            typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
        }
    }

    fun setThumbColors(@ColorInt accentThumbColor: Int) {
        val thumbColors = intArrayOf(thumbOffColor, accentThumbColor)
        DrawableCompat.setTintList(
            DrawableCompat.wrap(thumbDrawable),
            ColorStateList(states, thumbColors)
        )
    }

    fun setTrackColors(@ColorInt accentTrackColor: Int) {
        val trackColors = intArrayOf(trackOffColor, accentTrackColor)
        DrawableCompat.setTintList(
            DrawableCompat.wrap(trackDrawable),
            ColorStateList(states, trackColors)
        )
    }

    fun setHighlightColors(@ColorInt accentHighlightColor: Int) {
        val controlHighlightColors = intArrayOf(highlightOffColor, accentHighlightColor)
        val rippleDrawable = background as RippleDrawable
        rippleDrawable.setColor(ColorStateList(states, controlHighlightColors))
        background = rippleDrawable
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

    fun onThemeChanged(theme: FTLSwitchTheme) {
        setTextColor(ContextCompat.getColor(context, theme.textColor))
        thumbOffColor = ContextCompat.getColor(context, theme.thumbColor)
        trackOffColor = ContextCompat.getColor(context, theme.trackColor)
        highlightOffColor =
            ContextCompat.getColor(context, theme.highlightColor)
        setThumbColors(thumbOnColor)
        setTrackColors(trackOnColor)
        setHighlightColors(highlightOnColor)
    }
}

data class FTLSwitchTheme(
    @ColorRes val textColor: Int,
    @ColorRes val trackColor: Int,
    @ColorRes val thumbColor: Int,
    @ColorRes val highlightColor: Int
) : ViewTheme()
