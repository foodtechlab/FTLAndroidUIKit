package com.foodtechlab.ftlandroiduikit.button.circular

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import com.foodtechlab.ftlandroiduikit.util.changeColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by Umalt on 02.08.2021
 */
class FTLCircularCheckBox @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AppCompatCheckBox(context, attrs), CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLCircularCheckBoxTheme> =
        FTLCircularCheckBoxThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    @ColorInt
    var colorStrokeUnchecked = ContextCompat.getColor(context, R.color.IconGreyLight)
        set(value) {
            field = value
            drawableUnchecked?.mutate()?.changeColor(field)
            updateButtonDrawable()
        }

    @ColorInt
    var colorSolidChecked = ContextCompat.getColor(context, R.color.IconBlueLight)
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

    fun onThemeChanged(theme: FTLCircularCheckBoxTheme) {
        colorStrokeUnchecked = ContextCompat.getColor(
            context,
            theme.colorStrokeUnchecked
        )
        colorSolidChecked = ContextCompat.getColor(
            context,
            theme.colorSolidChecked
        )
    }

    private fun updateButtonDrawable() {
        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(states[0], drawableUnchecked)
        stateListDrawable.addState(states[1], drawableChecked)
        buttonDrawable = stateListDrawable
    }

    private fun TypedArray.updateThemeColorStrokeUnchecked() {
        viewThemeManager.lightTheme.colorStrokeUnchecked = getResourceId(
            R.styleable.FTLCircularCheckBox_theme_color_stroke_unchecked_light,
            viewThemeManager.lightTheme.colorStrokeUnchecked
        )
        viewThemeManager.darkTheme?.let {
            it.colorStrokeUnchecked = getResourceId(
                R.styleable.FTLCircularCheckBox_theme_color_stroke_unchecked_dark,
                it.colorStrokeUnchecked
            )
        }
    }

    private fun TypedArray.updateThemeColorSolidChecked() {
        viewThemeManager.lightTheme.colorSolidChecked = getResourceId(
            R.styleable.FTLCircularCheckBox_theme_color_stroke_unchecked_light,
            viewThemeManager.lightTheme.colorSolidChecked
        )
        viewThemeManager.darkTheme?.let {
            it.colorSolidChecked = getResourceId(
                R.styleable.FTLCircularCheckBox_theme_color_stroke_unchecked_dark,
                it.colorSolidChecked
            )
        }
    }
}

data class FTLCircularCheckBoxTheme(
    @ColorRes var colorStrokeUnchecked: Int,
    @ColorRes var colorSolidChecked: Int
) : ViewTheme()
