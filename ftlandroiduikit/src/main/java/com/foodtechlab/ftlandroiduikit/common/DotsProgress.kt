package com.foodtechlab.ftlandroiduikit.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation
import androidx.annotation.ColorInt
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import kotlin.math.min

/**
 * Created by Umalt on 18.06.2020
 */
class DotsProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val displayDensity = resources.displayMetrics.density

    private var origGap = 8 * displayDensity

    private var gap = 8 * displayDensity

    private var origDotRadius = 4 * displayDensity

    private var dotRadius = 4 * displayDensity

    private var origBounceDotRadius = 6 * displayDensity

    private var bounceDotRadius = 6 * displayDensity

    private var dotsCount = 3

    private var dotPosition = 0

    @ColorInt
    var dotColor = Color.WHITE

    @ColorInt
    var bounceDotColor = Color.WHITE

    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val bounceAnimation by lazy {
        BounceAnimation().apply {
            duration = 250
            repeatCount = Animation.INFINITE
            interpolator = LinearInterpolator()

            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {}

                override fun onAnimationRepeat(animation: Animation?) {
                    if (++dotPosition > dotsCount - 1) dotPosition = 0
                }
            })
        }
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.FTLDotsProgress) {
            dotsCount = getInt(R.styleable.FTLDotsProgress_dots_count, dotsCount)

            gap = getDimension(R.styleable.FTLDotsProgress_gap, gap)
            origGap = getDimension(R.styleable.FTLDotsProgress_gap, gap)

            dotColor = getColor(R.styleable.FTLDotsProgress_dot_color, dotColor)
            bounceDotColor = getColor(R.styleable.FTLDotsProgress_bounce_dot_color, dotColor)

            dotRadius = getDimension(R.styleable.FTLDotsProgress_dot_radius, dotRadius)
            origDotRadius = getDimension(R.styleable.FTLDotsProgress_dot_radius, dotRadius)

            bounceDotRadius = getDimension(
                R.styleable.FTLDotsProgress_dot_bounce_radius,
                bounceDotRadius
            )
            origBounceDotRadius = getDimension(
                R.styleable.FTLDotsProgress_dot_bounce_radius,
                bounceDotRadius
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawDots()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val hPadding = paddingStart + paddingEnd
        val vPadding = paddingTop + paddingBottom
        val dotsWidth =
            (2 + bounceDotRadius / dotRadius) * dotRadius * (dotsCount - 1) + 2 * bounceDotRadius
        val gapsWidth = gap * (dotsCount - 1)
        val desiredWidth = suggestedMinimumWidth + hPadding + gapsWidth + dotsWidth
        val desiredHeight = suggestedMinimumHeight + vPadding + 2 * bounceDotRadius

        setMeasuredDimension(
            measureDimension(desiredWidth.toInt(), widthMeasureSpec),
            measureDimension(desiredHeight.toInt(), heightMeasureSpec)
        )
    }

    private fun Canvas.drawDots() {
        val requiredWidth =
            2 * dotRadius * (dotsCount - 1) + 2 * bounceDotRadius + gap * (dotsCount - 1)

        for (i in 0 until dotsCount) {
            drawCircle(
                width * HALF - requiredWidth * HALF + i * gap + (2 + bounceDotRadius / dotRadius) * i * dotRadius,
                height * HALF,
                if (i == dotPosition) bounceDotRadius else dotRadius,
                dotPaint.apply {
                    color = if (i == dotPosition) bounceDotColor else dotColor
                }
            )
        }
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        val result = when (specMode) {
            MeasureSpec.EXACTLY -> specSize
            else -> {
                var tempRes = desiredSize
                if (specMode == MeasureSpec.AT_MOST) tempRes = min(tempRes, specSize)
                if (tempRes < desiredSize)
                    Log.e("DotsProgress", "The view is too small, the content might get cut")
                tempRes
            }
        }

        if (result < desiredSize) {
            val scale = result.toFloat() / desiredSize
            gap = min(gap, origGap * scale)
            dotRadius = min(dotRadius, origDotRadius * HALF)
            bounceDotRadius = min(bounceDotRadius, origBounceDotRadius * HALF)
        }

        return result
    }

    fun startAnimation() {
        startAnimation(bounceAnimation)
    }

    fun stopAnimation() {
        bounceAnimation.cancel()
        animation = null
    }

    private inner class BounceAnimation : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            super.applyTransformation(interpolatedTime, t)
            invalidate()
        }
    }

    companion object {
        private const val HALF = .5f
    }
}