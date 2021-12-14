package com.foodtechlab.ftlandroiduikit.progress.circle.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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

class FTLCircleProgressIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLCircleProgressIndicatorTheme> =
        FTLCircleProgressIndicatorThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    @ColorInt
    var progressBackgroundColor = ContextCompat.getColor(context, R.color.SurfaceFourthLight)
        private set(value) {
            field = value
            vBackground.background.changeColor(field)
        }

    @ColorInt
    var progressColor = ContextCompat.getColor(context, R.color.ProgressPrimaryLight)
        set(value) {
            field = value
            pbProgress.indeterminateDrawable.setTint(field)
        }

    var hideBackground = false
        set(value) {
            field = value
            vBackground.visibility = if (hideBackground) View.GONE else View.VISIBLE
        }

    private var vBackground: View
    private var pbProgress: ProgressBar

    init {
        inflate(context, R.layout.layout_ftl_circle_progress_indicator, this)

        vBackground = findViewById(R.id.v_background)
        pbProgress = findViewById(R.id.pb_progress)

        context.withStyledAttributes(attrs, R.styleable.FTLCircleProgressIndicator) {
            progressColor = getColor(
                R.styleable.FTLCircleProgressIndicator_progressColor,
                ContextCompat.getColor(context, R.color.ProgressPrimaryLight)
            )
            hideBackground = getBoolean(
                R.styleable.FTLCircleProgressIndicator_hideBackground,
                false
            )
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

    fun onThemeChanged(theme: FTLCircleProgressIndicatorTheme) {
        progressBackgroundColor =
            ContextCompat.getColor(context, theme.bgColor)
    }
}

data class FTLCircleProgressIndicatorTheme(
    @ColorRes val bgColor: Int
) : ViewTheme()
