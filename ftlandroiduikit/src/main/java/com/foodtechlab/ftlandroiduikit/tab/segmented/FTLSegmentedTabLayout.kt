package com.foodtechlab.ftlandroiduikit.tab.segmented

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.InsetDrawable
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.math.roundToInt

class FTLSegmentedTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TabLayout(context, attrs, defStyleAttr), CoroutineScope {
    private var viewThemeManager = FTLSegmentedTabLayoutThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var tabBackgroundColor: Int? = null
        set(value) {
            value?.let {
                background.setTint(it)
            }
            field = value
        }

    var tabIndicatorFullHeight: Boolean = true

    var tabIndicatorMargin: Int = 0

    init {
        context.withStyledAttributes(attrs, R.styleable.FTLSegmentedTabLayout) {
            tabIndicatorFullHeight =
                getBoolean(R.styleable.FTLSegmentedTabLayout_tabIndicatorFullHeight, false)
            tabIndicatorMargin =
                getDimension(R.styleable.FTLSegmentedTabLayout_tabIndicatorMargin, 0f).roundToInt()
            if (tabIndicatorFullHeight) {
                val newTabIndicator = InsetDrawable(tabSelectedIndicator, tabIndicatorMargin)
                setSelectedTabIndicator(newTabIndicator)
            }
            setupBackgroundColor()
            setupIndicatorColor()
        }

        onThemeChanged()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        if (tabIndicatorFullHeight) {
            tabSelectedIndicator.setBounds(
                tabSelectedIndicator.bounds.left,
                0,
                tabSelectedIndicator.bounds.right,
                height
            )
        }
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    private fun TypedArray.setupBackgroundColor() {
        viewThemeManager.lightTheme.backgroundColor =
            getResourceId(
                R.styleable.FTLSegmentedTabLayout_tabBackgroundColorLight,
                viewThemeManager.lightTheme.backgroundColor
            )
        viewThemeManager.darkTheme?.let {
            it.backgroundColor = getResourceId(
                R.styleable.FTLSegmentedTabLayout_tabBackgroundColorDark,
                it.backgroundColor
            )
        }
    }

    private fun TypedArray.setupIndicatorColor() {
        viewThemeManager.lightTheme.indicatorColor =
            getResourceId(
                R.styleable.FTLSegmentedTabLayout_tabIndicatorColorLight,
                viewThemeManager.lightTheme.indicatorColor
            )
        viewThemeManager.darkTheme?.let {
            it.indicatorColor = getResourceId(
                R.styleable.FTLSegmentedTabLayout_tabIndicatorColorDark,
                it.indicatorColor
            )
        }
    }

    private fun onThemeChanged() {
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                tabBackgroundColor = ContextCompat.getColor(
                    context,
                    theme.backgroundColor
                )
                setSelectedTabIndicatorColor(ContextCompat.getColor(context, theme.indicatorColor))

                requestLayout()
            }
        }
    }
}

data class FTLSegmentedTabLayoutTheme(
    @ColorRes var backgroundColor: Int,
    @ColorRes var indicatorColor: Int
) : ViewTheme()
