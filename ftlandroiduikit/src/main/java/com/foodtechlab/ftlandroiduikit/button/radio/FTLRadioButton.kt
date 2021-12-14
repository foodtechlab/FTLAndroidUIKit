package com.foodtechlab.ftlandroiduikit.button.radio

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

class FTLRadioButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AppCompatRadioButton(context, attrs), CoroutineScope {
    private val viewThemeManager = FTLRadioButtonThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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

    init {
        context.withStyledAttributes(attrs, R.styleable.FTLRadioButton) {
            colorForStateChecked = getColor(
                R.styleable.FTLRadioButton_colorForStateChecked,
                ContextCompat.getColor(context, R.color.ButtonDangerEnableLight)
            )
        }

        updateColorStyle(colorForStateChecked)

        textSize = 16f
        setLineSpacing(context.dpToPx(5f), 1.0f)
        typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)

        textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        layoutDirection = View.LAYOUT_DIRECTION_RTL

        val typedValue = TypedValue()
        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackgroundBorderless,
            typedValue,
            true
        )
        setBackgroundResource(typedValue.resourceId)
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

    private fun onThemeChanged(theme: FTLRadioButtonTheme) {
        setTextColor(ContextCompat.getColor(context, theme.textColor))
        colorForStateUnchecked =
            ContextCompat.getColor(context, theme.uncheckedStateColor)
    }

    fun updateColorStyle(@ColorInt checkedStateColor: Int) {
        val rbColors = intArrayOf(colorForStateUnchecked, checkedStateColor)
        buttonTintList = ColorStateList(states, rbColors)
    }
}

data class FTLRadioButtonTheme(
    @ColorRes val textColor: Int,
    @ColorRes val uncheckedStateColor: Int
) : ViewTheme()
