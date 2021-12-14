package com.foodtechlab.ftlandroiduikit.common.dotsprogress

import android.content.Context
import android.content.res.TypedArray
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
import androidx.annotation.ColorRes
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext
import kotlin.math.min

/**
 * Created by Umalt on 30.09.2020
 */
abstract class BaseDotsProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), CoroutineScope {

    private val displayDensity = resources.displayMetrics.density

    private var origGap = 8 * displayDensity

    private var gap = 8 * displayDensity

    private var origDotRadius = 4 * displayDensity

    private var dotRadius = 4 * displayDensity

    private var origBounceDotRadius = 6 * displayDensity

    private var bounceDotRadius = 6 * displayDensity

    private var dotsCount = 3

    private var animationSpeed = 250L

    private var dotPosition = 0

    abstract val viewThemeManager: ViewThemeManager<FTLDotsProgressTheme>
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    @ColorInt
    var dotColor = Color.WHITE

    @ColorInt
    var bounceDotColor = Color.WHITE

    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val bounceAnimation by lazy {
        BounceAnimation().apply {
            duration = animationSpeed
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

            animationSpeed =
                getInt(R.styleable.FTLDotsProgress_animation_speed, animationSpeed.toInt()).toLong()

            gap = getDimension(R.styleable.FTLDotsProgress_gap, gap)
            origGap = getDimension(R.styleable.FTLDotsProgress_gap, gap)

            setupDotColor()

            setupBounceDotColor()

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

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        job.cancel()
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
            bounceDotRadius =
                min(bounceDotRadius, origBounceDotRadius * HALF)
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

    protected abstract fun TypedArray.setupDotColor()

    protected abstract fun TypedArray.setupBounceDotColor()

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

data class FTLDotsProgressTheme(
    @ColorRes var dotColor: Int,
    @ColorRes var bounceDotColor: Int
) : ViewTheme()
