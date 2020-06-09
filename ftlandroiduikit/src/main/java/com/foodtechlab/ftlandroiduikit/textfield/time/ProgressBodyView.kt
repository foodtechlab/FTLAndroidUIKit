package com.foodtechlab.ftlandroiduikit.textfield.time

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.foodtechlab.ftlandroiduikit.util.dpToPx

class ProgressBodyView(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    private val bodyView = LinearLayout(context)

    var radius: Float = context.dpToPx(32f)
        set(value) {
            field = value
            updateHighlightView()
        }

    @Px
    var padding = context.dpToPx(0f)
        set(value) {
            field = value
            updateHighlightView()
        }

    @ColorInt
    var color = Color.BLACK
        set(value) {
            field = value
            updateHighlightView()
        }

    @ColorInt
    var colorGradientStart = 65555
        set(value) {
            field = value
            updateHighlightView()
        }

    @ColorInt
    var colorGradientEnd = 65555
        set(value) {
            field = value
            updateHighlightView()
        }

    var drawable: Drawable? = null
        set(value) {
            field = value
            updateHighlightView()
        }

    override fun onFinishInflate() {
        super.onFinishInflate()
        updateHighlightView()
    }

    fun updateHighlightView() {
        updateBodyView()
    }

    private fun updateBodyView() {
        if (colorGradientStart != 65555 && colorGradientEnd != 65555) {
            val gradientOrientation = GradientDrawable.Orientation.LEFT_RIGHT
            val gradient = GradientDrawable(
                gradientOrientation,
                intArrayOf(colorGradientStart, colorGradientEnd)
            )
            gradient.cornerRadius = radius
            this.bodyView.background = gradient
        } else if (this.drawable == null) {
            this.bodyView.background = GradientDrawable().apply {
                cornerRadius = radius
                setColor(this@ProgressBodyView.color)
            }
        } else {
            this.bodyView.background = this.drawable
        }
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ).apply {
            setMargins(padding.toInt(), padding.toInt(), padding.toInt(), padding.toInt())
        }
        this.bodyView.layoutParams = params
        removeView(bodyView)
        addView(bodyView)
    }
}
