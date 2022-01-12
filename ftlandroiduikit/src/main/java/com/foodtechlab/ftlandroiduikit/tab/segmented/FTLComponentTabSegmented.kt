package com.foodtechlab.ftlandroiduikit.tab.segmented

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FTLComponentTabSegmented @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), CoroutineScope {
    private var viewThemeManager = FTLComponentTabSegmentedThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var indicatorColor: Int? = null
        set(value) {
            field = value
            value?.let {
                ivIndicator.drawable.setTint(value)
            }
        }

    var title: String?
        get() = tvTitle.text.toString()
        set(value) {
            tvTitle.isVisible = !value.isNullOrEmpty()
            tvTitle.text = value
        }

    var isVisibleIcon: Boolean
        get() = ivIndicator.isVisible
        set(value) {
            ivIndicator.isVisible = value
        }

    var textColorStateList: ColorStateList
        get() = tvTitle.textColors
        set(value) {
            tvTitle.setTextColor(value)
        }

    private val tvTitle: TextView
    private val ivIndicator: ImageView

    init {
        inflate(context, R.layout.layout_ftl_component_tab_segmented, this)

        tvTitle = findViewById(R.id.tv_component_tab_title)
        ivIndicator = findViewById(R.id.iv_component_tab_indicator)

        gravity = Gravity.CENTER
        orientation = HORIZONTAL

        context.withStyledAttributes(attrs, R.styleable.FTLComponentTabSegmented) {
            setupIndicatorColor()
            setupTextColor()
        }

        onThemeChanged()
    }

    private fun TypedArray.setupIndicatorColor() {
        viewThemeManager.lightTheme.indicatorColor =
            getResourceId(
                R.styleable.FTLComponentTabSegmented_componentTab_IndicatorColorLight,
                viewThemeManager.lightTheme.indicatorColor
            )
        viewThemeManager.darkTheme?.let {
            it.indicatorColor = getResourceId(
                R.styleable.FTLComponentTabSegmented_componentTab_IndicatorColorDark,
                it.indicatorColor
            )
        }
    }

    private fun TypedArray.setupTextColor() {
        viewThemeManager.lightTheme.textColor =
            getResourceId(
                R.styleable.FTLComponentTabSegmented_tabTextColorLight,
                viewThemeManager.lightTheme.textColor
            )
        viewThemeManager.darkTheme?.let {
            it.textColor = getResourceId(
                R.styleable.FTLComponentTabSegmented_tabTextColorDark,
                it.textColor
            )
        }
        viewThemeManager.lightTheme.textSelectedColor =
            getResourceId(
                R.styleable.FTLComponentTabSegmented_tabTextSelectedColorLight,
                viewThemeManager.lightTheme.textSelectedColor
            )
        viewThemeManager.darkTheme?.let {
            it.textSelectedColor = getResourceId(
                R.styleable.FTLComponentTabSegmented_tabTextSelectedColorDark,
                it.textSelectedColor
            )
        }
    }

    private fun onThemeChanged() {
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                indicatorColor = ContextCompat.getColor(
                    context,
                    theme.indicatorColor
                )
                val textStates = arrayOf(
                    intArrayOf(-android.R.attr.state_selected),
                    intArrayOf(android.R.attr.state_selected)
                )
                val textColor = intArrayOf(
                    ContextCompat.getColor(context, theme.textColor),
                    ContextCompat.getColor(context, theme.textSelectedColor)
                )
                textColorStateList = ColorStateList(textStates, textColor)
            }
        }
    }
}

data class FTLComponentTabSegmentedTheme(
    @ColorRes var indicatorColor: Int,
    @ColorRes var textColor: Int,
    @ColorRes var textSelectedColor: Int,
) : ViewTheme()
