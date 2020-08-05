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
import com.foodtechlab.ftlandroiduikit.util.dpToPx


class FTLSwitch : SwitchCompat {
    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    @ColorInt
    private var thumbOnColor = ContextCompat.getColor(context, R.color.PrimaryDangerEnabled)

    @ColorInt
    private var thumbOffColor = ContextCompat.getColor(context, R.color.OnBackgroundSecondary)

    @ColorInt
    private var trackOnColor =
        ContextCompat.getColor(context, R.color.PrimaryDangerEnabledOpacity30)

    @ColorInt
    private var trackOffColor =
        ContextCompat.getColor(context, R.color.OnBackgroundSecondaryOpacity30)

    @ColorInt
    private var highlightOnColor =
        ContextCompat.getColor(context, R.color.PrimaryDangerEnabledOpacity30)

    @ColorInt
    private var highlightOffColor =
        ContextCompat.getColor(context, R.color.OnSurfacePrimaryAdditionalDark)

    private val states = arrayOf(
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_checked)
    )

    private fun init(attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.FTLSwitch) {
            thumbOnColor = getColor(
                R.styleable.FTLSwitch_thumbOnColor,
                ContextCompat.getColor(context, R.color.PrimaryDangerEnabled)
            )

            thumbOffColor = getColor(
                R.styleable.FTLSwitch_thumbOffColor,
                ContextCompat.getColor(context, R.color.OnBackgroundSecondary)
            )

            trackOnColor = getColor(
                R.styleable.FTLSwitch_trackOnColor,
                ContextCompat.getColor(context, R.color.PrimaryDangerEnabledOpacity30)
            )

            trackOffColor = getColor(
                R.styleable.FTLSwitch_trackOffColor,
                ContextCompat.getColor(context, R.color.OnBackgroundSecondaryOpacity30)
            )

            highlightOnColor = getColor(
                R.styleable.FTLSwitch_highlightOnColor,
                ContextCompat.getColor(context, R.color.PrimaryDangerEnabledOpacity30)
            )

            highlightOffColor = getColor(
                R.styleable.FTLSwitch_highlightOffColor,
                ContextCompat.getColor(context, R.color.OnSurfacePrimaryAdditionalDark)
            )
        }

        setTextColor(ContextCompat.getColor(context, R.color.OnSurfaceSecondary))
        textSize = 16f
        setLineSpacing(context.dpToPx(5f), 1.0f)

        if (!isInEditMode) {
            typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
        }

        setThumbColors(thumbOnColor, thumbOffColor)
        setTrackColors(trackOnColor, trackOffColor)
        setHighlightColors(highlightOnColor, highlightOffColor)
    }

    fun setThumbColors(@ColorInt onColor: Int, @ColorInt offColor: Int) {
        val thumbColors = intArrayOf(offColor, onColor)
        DrawableCompat.setTintList(
            DrawableCompat.wrap(thumbDrawable),
            ColorStateList(states, thumbColors)
        )
    }

    fun setTrackColors(@ColorInt onColor: Int, @ColorInt offColor: Int) {
        val trackColors = intArrayOf(offColor, onColor)
        DrawableCompat.setTintList(
            DrawableCompat.wrap(trackDrawable),
            ColorStateList(states, trackColors)
        )
    }

    fun setHighlightColors(@ColorInt onColor: Int, @ColorInt offColor: Int) {
        val controlHighlightColors = intArrayOf(offColor, onColor)
        val rippleDrawable = background as RippleDrawable
        rippleDrawable.setColor(ColorStateList(states, controlHighlightColors))
        background = rippleDrawable
    }
}