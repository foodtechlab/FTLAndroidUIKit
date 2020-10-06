package com.foodtechlab.ftlandroiduikit.switch

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.drawable.DrawableCompat
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPx


class FTLSwitch : SwitchCompat, ThemeManager.ThemeChangedListener {
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
    private var thumbOnColor = ContextCompat.getColor(context, R.color.PrimaryDangerEnabled)

    @ColorInt
    private var thumbOffColor = ContextCompat.getColor(context, R.color.ButtonSecondaryEnableLight)

    @ColorInt
    private var trackOnColor =
        ContextCompat.getColor(context, R.color.PrimaryDangerEnabledOpacity30)

    @ColorInt
    private var trackOffColor =
        ContextCompat.getColor(context, R.color.SwitchTrackEnableLight)

    @ColorInt
    private var highlightOnColor =
        ContextCompat.getColor(context, R.color.PrimaryDangerEnabledOpacity30)

    @ColorInt
    private var highlightOffColor =
        ContextCompat.getColor(context, R.color.SwitchTrackEnableLight)

    private val states = arrayOf(
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_checked)
    )

    private fun init(attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.FTLSwitch) {
            thumbOnColor = getColor(
                R.styleable.FTLSwitch_thumbAccentColor,
                ContextCompat.getColor(context, R.color.PrimaryDangerEnabled)
            )

            trackOnColor = getColor(
                R.styleable.FTLSwitch_trackAccentColor,
                ContextCompat.getColor(context, R.color.PrimaryDangerEnabledOpacity30)
            )

            highlightOnColor = getColor(
                R.styleable.FTLSwitch_highlightAccentColor,
                ContextCompat.getColor(context, R.color.PrimaryDangerEnabledOpacity30)
            )
        }

        textSize = 16f
        setLineSpacing(context.dpToPx(5f), 1.0f)

        if (!isInEditMode) {
            typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
        }

        onThemeChanged(ThemeManager.theme)
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
        setTextColor(ContextCompat.getColor(context, theme.ftlSwitchTheme.textColor))
        thumbOffColor = ContextCompat.getColor(context, theme.ftlSwitchTheme.thumbColor)
        trackOffColor = ContextCompat.getColor(context, theme.ftlSwitchTheme.trackColor)
        highlightOffColor = ContextCompat.getColor(context, theme.ftlSwitchTheme.highlightColor)
        setThumbColors(thumbOnColor)
        setTrackColors(trackOnColor)
        setHighlightColors(highlightOnColor)
    }

}