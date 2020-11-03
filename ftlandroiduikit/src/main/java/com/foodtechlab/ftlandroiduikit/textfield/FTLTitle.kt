package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager

class FTLTitle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {

    private val displayDensity = resources.displayMetrics.density

    var autoHandleColors = true

    var isTitleVisible: Boolean
        get() = tvTitle.isVisible
        set(value) {
            tvTitle.isVisible = value
        }

    var isSubtitleVisible: Boolean
        get() = tvTitle.isVisible
        set(value) {
            tvSubtitle.isVisible = value
        }

    var titleColor: Int
        get() = tvTitle.currentTextColor
        set(value) {
            tvTitle.setTextColor(value)
        }

    var subtitleColor: Int
        get() = tvSubtitle.currentTextColor
        set(value) {
            tvSubtitle.setTextColor(value)
        }

    var title: String?
        get() = tvTitle.text.toString()
        set(value) {
            tvTitle.isVisible = !value.isNullOrEmpty()
            tvTitle.text = value
        }

    var subTitle: String?
        get() = tvSubtitle.text.toString()
        set(value) {
            tvSubtitle.isVisible = !value.isNullOrEmpty()
            tvSubtitle.text = value
        }

    private val tvTitle: TextView
    private val tvSubtitle: TextView

    init {
        View.inflate(context, R.layout.layout_ftl_title, this)

        orientation = VERTICAL

        tvTitle = findViewById(R.id.tv_ftl_title)
        tvSubtitle = findViewById(R.id.tv_ftl_subtitle)

        context.withStyledAttributes(attrs, R.styleable.FTLTitle) {
            val pStart = if (paddingStart == 0) (16f * displayDensity).toInt() else paddingStart
            val pTop = if (paddingTop == 0) (8f * displayDensity).toInt() else paddingTop
            val pEnd = if (paddingEnd == 0) (16f * displayDensity).toInt() else paddingEnd
            val pBottom = if (paddingBottom == 0) (8f * displayDensity).toInt() else paddingBottom

            setPadding(pStart, pTop, pEnd, pBottom)

            setupTitleColor()

            setupSubtitleColor()

            title = getString(R.styleable.FTLTitle_title_text)
            subTitle = getString(R.styleable.FTLTitle_subtitle_text)
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

    private fun TypedArray.setupTitleColor() {
        ThemeManager.Theme.LIGHT.ftlTitleTheme.titleColor = getResourceId(
            R.styleable.FTLTitle_title_color_light,
            R.color.TextPrimaryLight
        )
        ThemeManager.Theme.DARK.ftlTitleTheme.titleColor = getResourceId(
            R.styleable.FTLTitle_title_color_dark,
            R.color.TextPrimaryDark
        )
        titleColor = ContextCompat.getColor(context, ThemeManager.theme.ftlTitleTheme.titleColor)
    }

    private fun TypedArray.setupSubtitleColor() {
        ThemeManager.Theme.LIGHT.ftlTitleTheme.subtitleColor = getResourceId(
            R.styleable.FTLTitle_subtitle_color_light,
            R.color.TextSuccessEnabledLight
        )
        ThemeManager.Theme.DARK.ftlTitleTheme.subtitleColor = getResourceId(
            R.styleable.FTLTitle_subtitle_color_dark,
            R.color.TextSuccessEnabledDark
        )
        subtitleColor = ContextCompat.getColor(
            context,
            ThemeManager.theme.ftlTitleTheme.subtitleColor
        )
    }

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        if (autoHandleColors) {
            titleColor = ContextCompat.getColor(context, theme.ftlTitleTheme.titleColor)
            subtitleColor = ContextCompat.getColor(context, theme.ftlTitleTheme.subtitleColor)
        }
    }
}