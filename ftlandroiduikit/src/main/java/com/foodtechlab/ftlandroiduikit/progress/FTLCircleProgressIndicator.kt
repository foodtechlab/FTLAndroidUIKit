package com.foodtechlab.ftlandroiduikit.progress

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.changeColor

class FTLCircleProgressIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {

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
        inflate(context, R.layout.layout_ftl_progress, this)

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
        progressBackgroundColor =
            ContextCompat.getColor(context, theme.ftlCircleProgressIndicatorTheme.bgColor)
    }
}