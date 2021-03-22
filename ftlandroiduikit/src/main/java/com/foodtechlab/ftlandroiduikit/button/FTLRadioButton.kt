package com.foodtechlab.ftlandroiduikit.button

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPx

class FTLRadioButton : AppCompatRadioButton, ThemeManager.ThemeChangedListener {
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
    private var colorForStateChecked =
        ContextCompat.getColor(context, R.color.ButtonDangerEnableLight)

    @ColorInt
    private var colorForStateUnchecked =
        ContextCompat.getColor(context, R.color.ButtonSecondaryEnableLight)

    private val states = arrayOf(
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_checked)
    )

    private fun init(attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.FTLRadioButton) {
            colorForStateChecked = getColor(
                R.styleable.FTLRadioButton_colorForStateChecked,
                ContextCompat.getColor(context, R.color.ButtonDangerEnableLight)
            )
        }
        onThemeChanged(ThemeManager.theme)

        textSize = 16f
        setLineSpacing(context.dpToPx(5f), 1.0f)
        typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)

        textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        layoutDirection = View.LAYOUT_DIRECTION_RTL

        val typedValue = TypedValue()
        context.theme
            .resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, typedValue, true)
        setBackgroundResource(typedValue.resourceId)
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
        setTextColor(ContextCompat.getColor(context, theme.ftlRadioButtonTheme.textColor))
        colorForStateUnchecked =
            ContextCompat.getColor(context, theme.ftlRadioButtonTheme.uncheckedStateColor)
        updateColorStyle(colorForStateChecked)
    }

    fun updateColorStyle(@ColorInt checkedStateColor: Int) {
        val rbColors = intArrayOf(colorForStateUnchecked, checkedStateColor)
        buttonTintList = ColorStateList(states, rbColors)
    }
}