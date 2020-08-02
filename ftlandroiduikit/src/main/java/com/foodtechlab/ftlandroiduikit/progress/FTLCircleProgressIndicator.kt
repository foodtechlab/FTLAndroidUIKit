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


class FTLCircleProgressIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var vBackground: View
    private var pbProgress: ProgressBar

    @ColorInt
    var progressBackgroundColor = ContextCompat.getColor(context, R.color.OnPrimary)
        set(value) {
            field = value
            vBackground.background.setTint(field)
        }

    @ColorInt
    var progressColor = ContextCompat.getColor(context, R.color.PrimaryInfoEnabled)
        set(value) {
            field = value
            pbProgress.indeterminateDrawable.setTint(field)
        }

    var hideBackground = false
        set(value) {
            field = value
            vBackground.visibility = if (hideBackground) View.GONE else View.VISIBLE
        }

    init {
        inflate(context, R.layout.layout_ftl_progress, this)

        vBackground = findViewById(R.id.v_background)
        pbProgress = findViewById(R.id.pb_progress)

        context.withStyledAttributes(attrs, R.styleable.FTLCircleProgressIndicator) {
            progressColor = getColor(
                R.styleable.FTLCircleProgressIndicator_progressColor,
                ContextCompat.getColor(context, R.color.PrimaryInfoEnabled)
            )
            progressBackgroundColor = getColor(
                R.styleable.FTLCircleProgressIndicator_progressBackgroundColor,
                ContextCompat.getColor(context, R.color.OnPrimary)
            )
            hideBackground =
                getBoolean(R.styleable.FTLCircleProgressIndicator_hideBackground, false)
        }
    }
}