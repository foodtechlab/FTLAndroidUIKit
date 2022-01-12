package com.foodtechlab.ftlandroiduikit.textfield.indicator

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

class FTLIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLIndicatorViewTheme> =
        FTLIndicatorViewThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var title: String?
        get() = tvTitle.text.toString()
        set(value) {
            tvTitle.isVisible = !value.isNullOrEmpty()
            tvTitle.text = value
        }

    var textColor: Int
        get() = tvTitle.currentTextColor
        set(value) {
            tvTitle.setTextColor(value)
        }

    var icon: Drawable?
        get() = ivIndicator.drawable
        set(value) {
            value?.let {
                ivIndicator.setImageDrawable(value)
            }
        }

    var isVisibleIcon: Boolean
        get() = ivIndicator.isVisible
        set(value) {
            ivIndicator.isVisible = value
        }

    var iconColor: Int? = null
        set(value) {
            field = value
            iconColor?.let {
                ivIndicator.drawable.setTint(it)
            }
        }

    private val tvTitle: TextView
    private val ivIndicator: ImageView

    init {
        View.inflate(context, R.layout.layout_ftl_indicator, this)

        tvTitle = findViewById(R.id.tv_ftl_indicator)
        ivIndicator = findViewById(R.id.iv_ftl_indicator)

        context.withStyledAttributes(attrs, R.styleable.FTLIndicator) {
            title = getString(R.styleable.FTLIndicator_text_indicator)
            icon = getDrawable(R.styleable.FTLIndicator_icon_indicator)
            isVisibleIcon = getBoolean(R.styleable.FTLIndicator_icon_indicator, false)

            setupTitleColor()
            setupIconColor()
        }

        onThemeChanged()
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    private fun onThemeChanged() {
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                textColor = ContextCompat.getColor(
                    context,
                    theme.titleColor
                )
                theme.iconColor?.let {
                    iconColor = ContextCompat.getColor(
                        context,
                        it
                    )
                }
            }
        }
    }

    private fun TypedArray.setupTitleColor() {
        viewThemeManager.lightTheme.titleColor =
            getResourceId(
                R.styleable.FTLIndicator_indicator_text_color_light,
                viewThemeManager.lightTheme.titleColor
            )
        viewThemeManager.darkTheme?.let {
            it.titleColor = getResourceId(
                R.styleable.FTLIndicator_indicator_text_color_dark,
                it.titleColor
            )
        }
    }

    private fun TypedArray.setupIconColor() {
        try {
            viewThemeManager.lightTheme.iconColor =
                getResourceIdOrThrow(R.styleable.FTLIndicator_indicator_icon_color_light)
        } catch (e: IllegalArgumentException) {
            viewThemeManager.lightTheme.iconColor = null
        }

        try {
            viewThemeManager.darkTheme?.let {
                it.iconColor = getResourceIdOrThrow(
                    R.styleable.FTLIndicator_indicator_icon_color_dark,
                )
            }
        } catch (e: IllegalArgumentException) {
            viewThemeManager.darkTheme!!.iconColor = null
        }
    }

    companion object {
        const val TAG = "FTLIndicatorView"
    }
}

data class FTLIndicatorViewTheme(
    @ColorRes var titleColor: Int,
    @ColorRes var iconColor: Int?
) : ViewTheme()
