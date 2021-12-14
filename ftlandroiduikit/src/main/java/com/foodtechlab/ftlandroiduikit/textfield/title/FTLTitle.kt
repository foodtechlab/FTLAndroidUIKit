package com.foodtechlab.ftlandroiduikit.textfield.title

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

class FTLTitle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLTitleTheme> =
        FTLTitleThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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

    private fun TypedArray.setupTitleColor() {
        viewThemeManager.lightTheme.titleColor = getResourceId(
            R.styleable.FTLTitle_title_color_light,
            R.color.TextPrimaryLight
        )
        viewThemeManager.darkTheme?.titleColor = getResourceId(
            R.styleable.FTLTitle_title_color_dark,
            R.color.TextPrimaryDark
        )
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                titleColor = ContextCompat.getColor(context, theme.titleColor)
            }
            try {
                this.cancel()
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
            }
        }
    }

    private fun TypedArray.setupSubtitleColor() {
        viewThemeManager.lightTheme.subtitleColor = getResourceId(
            R.styleable.FTLTitle_title_color_light,
            R.color.TextSuccessEnabledDark
        )
        viewThemeManager.darkTheme?.subtitleColor = getResourceId(
            R.styleable.FTLTitle_title_color_dark,
            R.color.TextSuccessEnabledDark
        )
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                subtitleColor = ContextCompat.getColor(
                    context,
                    theme.subtitleColor
                )
                try {
                    this.cancel()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    private fun onThemeChanged(theme: FTLTitleTheme) {
        if (autoHandleColors) {
            titleColor = ContextCompat.getColor(context, theme.titleColor)
            subtitleColor = ContextCompat.getColor(context, theme.subtitleColor)
        }
    }

    companion object {
        private const val TAG = "FTLTitle"
    }
}

data class FTLTitleTheme(
    @ColorRes var titleColor: Int,
    @ColorRes var subtitleColor: Int
) : ViewTheme()
