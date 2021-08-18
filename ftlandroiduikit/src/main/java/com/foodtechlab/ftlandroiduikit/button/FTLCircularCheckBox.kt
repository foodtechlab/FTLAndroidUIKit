package com.foodtechlab.ftlandroiduikit.button

import android.content.Context
import android.content.res.TypedArray
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
import com.foodtechlab.ftlandroiduikit.util.changeColor

/**
 * Created by Umalt on 02.08.2021
 */
class FTLCircularCheckBox @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AppCompatCheckBox(context, attrs), ThemeManager.ThemeChangedListener {
    @ColorInt
    var colorStrokeUnchecked = ContextCompat.getColor(context, R.color.IconGreyLight)
        set(value) {
            field = value
            drawableUnchecked?.mutate()?.changeColor(field)
            updateButtonDrawable()
        }

    @ColorInt
    var colorSolidChecked = ContextCompat.getColor(context, R.color.ButtonInfoEnableLight)
        set(value) {
            field = value
            val checkedGradientDrawable =
                drawableChecked?.findDrawableByLayerId(R.id.fg_checked)
            checkedGradientDrawable?.mutate()?.changeColor(field)
            updateButtonDrawable()
        }

    private val drawableChecked = ResourcesCompat.getDrawable(
        resources,
        R.drawable.layer_list_ftl_circular_checkbox_checked,
        null
    ) as? LayerDrawable

    private val drawableUnchecked = ContextCompat.getDrawable(
        context,
        R.drawable.img_ftl_curcular_checkbox_unchecked
    )

    private val states = arrayOf(
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_checked)
    )

    init {
        context.withStyledAttributes(attrs, R.styleable.FTLCircularCheckBox) {
            updateThemeColorStrokeUnchecked()
            updateThemeColorSolidChecked()
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
        colorStrokeUnchecked = ContextCompat.getColor(
            context,
            theme.ftlCircularCheckBoxTheme.colorStrokeUnchecked
        )
        colorSolidChecked = ContextCompat.getColor(
            context,
            theme.ftlCircularCheckBoxTheme.colorSolidChecked
        )
    }

    private fun updateButtonDrawable() {
        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(states[0], drawableUnchecked)
        stateListDrawable.addState(states[1], drawableChecked)
        buttonDrawable = stateListDrawable
    }

    private fun TypedArray.updateThemeColorStrokeUnchecked() {
        ThemeManager.Theme.LIGHT.ftlCircularCheckBoxTheme.colorStrokeUnchecked = getResourceId(
            R.styleable.FTLCircularCheckBox_theme_color_stroke_unchecked_light,
            ThemeManager.Theme.LIGHT.ftlCircularCheckBoxTheme.colorStrokeUnchecked
        )
        ThemeManager.Theme.DARK.ftlCircularCheckBoxTheme.colorStrokeUnchecked = getResourceId(
            R.styleable.FTLCircularCheckBox_theme_color_stroke_unchecked_dark,
            ThemeManager.Theme.DARK.ftlCircularCheckBoxTheme.colorStrokeUnchecked
        )
    }

    private fun TypedArray.updateThemeColorSolidChecked() {
        ThemeManager.Theme.LIGHT.ftlCircularCheckBoxTheme.colorSolidChecked = getResourceId(
            R.styleable.FTLCircularCheckBox_theme_color_solid_checked_light,
            ThemeManager.Theme.LIGHT.ftlCircularCheckBoxTheme.colorSolidChecked
        )
        ThemeManager.Theme.DARK.ftlCircularCheckBoxTheme.colorSolidChecked = getResourceId(
            R.styleable.FTLCircularCheckBox_theme_color_solid_checked_dark,
            ThemeManager.Theme.DARK.ftlCircularCheckBoxTheme.colorSolidChecked
        )
    }
}