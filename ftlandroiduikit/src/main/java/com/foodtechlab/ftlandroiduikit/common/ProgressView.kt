package com.foodtechlab.ftlandroiduikit.common

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.GradientDrawable
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.Px
import androidx.core.content.withStyledAttributes
import androidx.core.view.updateLayoutParams
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.timer.OnProgressChangeListener
import com.foodtechlab.ftlandroiduikit.util.doStartAndFinish
import com.foodtechlab.ftlandroiduikit.util.dpToPx

/**
 * ProgressView is a progress bar with a flexible text and animations.
 * */
class ProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    // Presents background color of the ProgressView
    private val progressBodyView = ProgressBodyView(context)

    // Duration of the progress animation
    var duration = 1000L

    // Returns the ProgressView's animation is ongoing or not
    var isAnimating = false

    // Starts progress animation automatically when [ProgressView] is initialized
    var autoAnimate = true

    // Minimum value of the progress
    var min = 0f

    // Maximum value of the progress
    var max = 100f
        set(value) {
            field = value
            updateProgressView()
        }

    // A field for holding previous progressed value
    private var previousProgress = 0f

    // starts progress animation from the previousProgress to a new progress value
    private var progressFromPrevious = false
        set(value) {
            field = value
            previousProgress = 0f
        }

    // Presents the progress value of the ProgressView
    var progress = 0f
        set(value) {
            if (progressFromPrevious) {
                previousProgress = field
            }
            field = when {
                value >= max -> max
                value <= min -> min
                else -> value
            }
            updateProgressView()
            onProgressChangeListener?.onChange(field)
        }

    // Background color of the ProgressView's container
    @ColorInt
    var colorBackground = Color.WHITE
        set(value) {
            field = value
            updateProgressView()
        }

    var colorProgress: Int
        get() = progressBodyView.color
        set(value) {
            progressBodyView.color = value
        }

    // Corner radius of the ProgressView's container
    @Px
    var radius = context.dpToPx(5f)
        set(value) {
            field = value
            updateProgressView()
        }

    // Interface for listening to the progress is changed
    private var onProgressChangeListener: OnProgressChangeListener? = null

    // Path for smoothing the container's corner
    private val path = Path()

    init {
        context.withStyledAttributes(attrs, R.styleable.ProgressView) {
            min = getFloat(R.styleable.ProgressView_progressView_min, min)
            max = getFloat(R.styleable.ProgressView_progressView_max, max)
            progress = getFloat(R.styleable.ProgressView_progressView_progress, progress)
            radius = getDimension(R.styleable.ProgressView_progressView_radius, radius)
            duration = getInteger(
                R.styleable.ProgressView_progressView_duration,
                duration.toInt()
            ).toLong()
            colorBackground = getColor(
                R.styleable.ProgressView_progressView_colorBackground,
                colorBackground
            )
            autoAnimate = getBoolean(R.styleable.ProgressView_progressView_autoAnimate, autoAnimate)
            progressFromPrevious = getBoolean(
                R.styleable.ProgressView_progressView_progressFromPrevious,
                progressFromPrevious
            )

            with(progressBodyView) {
                colorProgress = getColor(R.styleable.ProgressView_progressView_colorProgress, color)
                colorGradientStart = getColor(
                    R.styleable.ProgressView_progressView_colorGradientStart,
                    65555
                )
                colorGradientEnd = getColor(
                    R.styleable.ProgressView_progressView_colorGradientEnd,
                    65555
                )
                radius = getDimension(R.styleable.ProgressView_progressView_radius, radius)
                padding = getDimension(R.styleable.ProgressView_progressView_padding, padding)
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable? =
        SavedState(
            super.onSaveInstanceState()
        ).apply {
            progressFromPrevious = this@ProgressView.progressFromPrevious
            progress = this@ProgressView.progress
            previousProgress = this@ProgressView.previousProgress
        }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            progressFromPrevious = state.progressFromPrevious
            progress = state.progress
            previousProgress = state.previousProgress
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        updateProgressView()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.path.apply {
            reset()
            addRoundRect(
                RectF(0f, 0f, w.toFloat(), h.toFloat()),
                floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius),
                Path.Direction.CCW
            )
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.clipPath(this.path)
        super.dispatchDraw(canvas)
    }

    private fun updateProgressView() {
        updateBackground()
        post {
            updateProgressBodyView()
            if (autoAnimate) {
                progressAnimate()
            }
        }
    }

    private fun updateBackground() {
        this.background = GradientDrawable().apply {
            cornerRadius = radius
            setColor(colorBackground)
        }
    }

    private fun updateProgressBodyView() {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        params.width = if (max <= progress) width else getProgressSize().toInt()
        this.progressBodyView.layoutParams = params
        this.progressBodyView.updateHighlightView()
        removeView(progressBodyView)
        addView(progressBodyView)
    }

    private fun getProgressSize(progressValue: Float = progress): Float {
        return (width / max) * progressValue
    }

    private fun getPreviousMergedProgressSize(
        @FloatRange(from = 0.0, to = 1.0) progressRange: Float
    ): Float {
        val curProgressSize = getProgressSize()
        val prevProgressSize = getProgressSize(previousProgress)
        return if (prevProgressSize + curProgressSize * progressRange <= curProgressSize) {
            prevProgressSize + curProgressSize * progressRange
        } else {
            getProgressSize()
        }
    }

    /**
     * Animates [ProgressView]'s progress bar.
     * */
    private fun progressAnimate() {
        ValueAnimator.ofFloat(0f, 1f)
            .apply {
                interpolator = AccelerateInterpolator()
                duration = this@ProgressView.duration
                addUpdateListener {
                    val value = it.animatedValue as Float
                    progressBodyView.updateLayoutParams {
                        width = getPreviousMergedProgressSize(value).toInt()
                    }
                }
                doStartAndFinish(
                    start = { isAnimating = true },
                    finish = { isAnimating = false }
                )
            }
            .also { it.start() }
    }

    fun isProgressedMax(): Boolean {
        return progress == max
    }

    fun isProgressedMin(): Boolean {
        return progress == min
    }

    /**
     * Sets a progress change listener
     * */
    fun setOnProgressChangeListener(onProgressChangeListener: OnProgressChangeListener) {
        this.onProgressChangeListener = onProgressChangeListener
    }

    /**
     * Sets a progress change listener
     * */
    fun setOnProgressChangeListener(block: (Float) -> Unit) {
        this.onProgressChangeListener = object :
            OnProgressChangeListener {
            override fun onChange(progress: Float) {
                block(progress)
            }
        }
    }

    internal class SavedState : BaseSavedState {
        var progressFromPrevious = false

        var progress = 0f
        var previousProgress = 0f

        constructor(superState: Parcelable?) : super(superState)

        constructor(parcel: Parcel) : super(parcel) {
            progressFromPrevious = parcel.readByte() != 0.toByte()
            progress = parcel.readFloat()
            previousProgress = parcel.readFloat()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)
            parcel.writeByte(if (progressFromPrevious) 1 else 0)
            parcel.writeFloat(progress)
            parcel.writeFloat(previousProgress)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(
                    parcel
                )
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}