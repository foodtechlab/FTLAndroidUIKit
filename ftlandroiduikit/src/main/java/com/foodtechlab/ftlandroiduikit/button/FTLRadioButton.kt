package com.foodtechlab.ftlandroiduikit.button

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.updatePadding
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.dpToPx

class FTLRadioButton : AppCompatRadioButton {
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
    var colorStyle = ContextCompat.getColor(context, R.color.PrimaryDangerEnabled)
        set(value) {
            field = value
            updateColorStyle(field)
        }

    private fun init(attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.FTLRadioButton) {
            colorStyle = getColor(
                R.styleable.FTLRadioButton_accentColor,
                ContextCompat.getColor(context, R.color.PrimaryDangerEnabled)
            )
        }
        updateColorStyle(colorStyle)

        setTextColor(ContextCompat.getColor(context, R.color.OnSurfaceSecondary))
        textSize = 16f
        setLineSpacing(context.dpToPx(5f), 1.0f)
        typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)

        buttonDrawable = null
        val typedValue = TypedValue()
        context.theme
            .resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
        setBackgroundResource(typedValue.resourceId)

        updatePaddingForComponent()
    }

    private fun updateColorStyle(@ColorInt color: Int) {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(
            android.R.attr.listChoiceIndicatorSingle,
            typedValue,
            true
        )
        val drawable = ContextCompat.getDrawable(context, typedValue.resourceId)
        drawable?.setTint(color)

        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
    }

    fun updatePaddingForComponent(
        start: Float = PADDING_START_DEFAULT,
        top: Float = PADDING_TOP_BOTTOM_DEFAULT,
        end: Float = PADDING_END_DEFAULT,
        bottom: Float = PADDING_TOP_BOTTOM_DEFAULT
    ) {
        updatePadding(
            context.dpToPx(start).toInt(),
            context.dpToPx(top).toInt(),
            context.dpToPx(end).toInt(),
            context.dpToPx(bottom).toInt()
        )
    }

    companion object {
        const val PADDING_TOP_BOTTOM_DEFAULT = 8f
        const val PADDING_START_DEFAULT = 16f
        const val PADDING_END_DEFAULT = 14f
    }
}