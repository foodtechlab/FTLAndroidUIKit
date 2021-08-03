package com.foodtechlab.ftlandroiduikit.button

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.dpToPxInt

/**
 * Created by Umalt on 02.08.2021
 */
class FTLCircularCheckBox @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AppCompatCheckBox(context, attrs), ThemeManager.ThemeChangedListener {
    @ColorInt
    private var strokeColorChecked = ContextCompat.getColor(
        context,
        R.color.IconBlueLight
    )

    @ColorInt
    private var strokeColorUnchecked = ContextCompat.getColor(
        context,
        R.color.IconGreyLight
    )

    @ColorInt
    private var solidColorChecked = ContextCompat.getColor(
        context,
        R.color.IconBlueLight
    )

    private val drawableChecked = ResourcesCompat.getDrawable(
        resources,
        R.drawable.layer_list_ftl_circular_checkbox_checked,
        null
    ) as? LayerDrawable

    private val drawableUnchecked = ResourcesCompat.getDrawable(
        resources,
        R.drawable.shape_ftl_circular_checkbox_unchecked,
        null
    ) as? GradientDrawable

    private val states = arrayOf(
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_checked)
    )

    init {
        context.withStyledAttributes(attrs, R.styleable.FTLCircularCheckBox) {
            updateStrokeColorChecked()
            updateStrokeColorUnchecked()
            updateSolidColorChecked()
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
        strokeColorChecked = ContextCompat.getColor(
            context,
            theme.ftlCircularCheckBoxTheme.strokeColorChecked
        )
        strokeColorUnchecked = ContextCompat.getColor(
            context,
            theme.ftlCircularCheckBoxTheme.strokeColorUnchecked
        )
        solidColorChecked = ContextCompat.getColor(
            context,
            theme.ftlCircularCheckBoxTheme.solidColorChecked
        )
        updateColors()
    }

    private fun updateColors() {
        val checkedGradientDrawable =
            drawableChecked?.findDrawableByLayerId(R.id.qwerty) as? GradientDrawable
        checkedGradientDrawable?.setColor(solidColorChecked)
        checkedGradientDrawable?.setStroke(context.dpToPxInt(2f), strokeColorChecked)

        drawableUnchecked?.setStroke(context.dpToPxInt(2f), strokeColorUnchecked)

        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(states[0], drawableUnchecked)
        stateListDrawable.addState(states[1], drawableChecked)

        buttonDrawable = stateListDrawable
    }

    private fun TypedArray.updateStrokeColorChecked() {
        ThemeManager.Theme.LIGHT.ftlCircularCheckBoxTheme.strokeColorChecked = getResourceId(
            R.styleable.FTLCircularCheckBox_color_stroke_checked_light,
            ThemeManager.Theme.LIGHT.ftlCircularCheckBoxTheme.strokeColorChecked
        )
        ThemeManager.Theme.DARK.ftlCircularCheckBoxTheme.strokeColorUnchecked = getResourceId(
            R.styleable.FTLCircularCheckBox_color_stroke_checked_dark,
            ThemeManager.Theme.DARK.ftlCircularCheckBoxTheme.strokeColorChecked
        )
    }

    private fun TypedArray.updateStrokeColorUnchecked() {
        ThemeManager.Theme.LIGHT.ftlCircularCheckBoxTheme.strokeColorUnchecked = getResourceId(
            R.styleable.FTLCircularCheckBox_color_stroke_unchecked_light,
            ThemeManager.Theme.LIGHT.ftlCircularCheckBoxTheme.strokeColorUnchecked
        )
        ThemeManager.Theme.DARK.ftlCircularCheckBoxTheme.strokeColorUnchecked = getResourceId(
            R.styleable.FTLCircularCheckBox_color_stroke_unchecked_dark,
            ThemeManager.Theme.DARK.ftlCircularCheckBoxTheme.strokeColorUnchecked
        )
    }

    private fun TypedArray.updateSolidColorChecked() {
        ThemeManager.Theme.LIGHT.ftlCircularCheckBoxTheme.solidColorChecked = getResourceId(
            R.styleable.FTLCircularCheckBox_color_solid_checked_light,
            ThemeManager.Theme.LIGHT.ftlCircularCheckBoxTheme.solidColorChecked
        )
        ThemeManager.Theme.DARK.ftlCircularCheckBoxTheme.solidColorChecked = getResourceId(
            R.styleable.FTLCircularCheckBox_color_solid_checked_dark,
            ThemeManager.Theme.DARK.ftlCircularCheckBoxTheme.solidColorChecked
        )
    }
}