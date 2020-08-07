package com.foodtechlab.ftlandroiduikit.button.timer

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Handler
import android.os.Looper
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.DotsProgress
import com.foodtechlab.ftlandroiduikit.common.ProgressView
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.getMillis
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer
import kotlin.math.abs

/**
 * Created by Umalt on 16.06.2020
 */
class FTLTimerButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val radius = 8 * resources.displayMetrics.density

    private var isTimerRenewableInside = true

    var inProgress = false
        private set

    var autoAnimateProgress: Boolean
        get() = progressView.autoAnimate
        set(value) {
            progressView.autoAnimate = value
        }

    @ColorRes
    var textColorRes = -1

    @ColorRes
    var dotColorRes = -1

    @ColorRes
    var bounceDotColorRes = -1

    var estimateDuration = 0L

    private var remainedDuration = 0L
        private set(value) {
            field = value

            val hours = TimeUnit.MILLISECONDS.toHours(value)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(value) % 60 + hours * 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(value - minutes * 60 * 1000) % 3600

            val format = if (seconds < 0 || minutes < 0) {
                R.string.format_negative_mm_ss
            } else {
                R.string.format_mm_ss
            }

            deliveryTime = String.format(context.getString(format), abs(minutes), abs(seconds))
        }

    private var state = State.NEW

    var deliveryTime: String? = if (isInEditMode) "00:00" else null
        set(value) {
            field = value
            tvTime.text = value
        }

    var timeZoneId: String? = null

    var estimateSuccessAt: String? = null
        set(value) {
            field = value
            if (isTimerRenewableInside) {
                launchTimerTask(getMillis(value, timeZoneId) - System.currentTimeMillis())
            }
        }

    private val fadeTransition = Fade().apply { duration = 200 }

    private var timer: Timer? = null

    private var clickListener: OnClickListener? = null

    private val rlRoot: RelativeLayout
    private val llContainer: LinearLayout
    private val progressView: ProgressView
    private val tvTime: TextView
    private val tvLabel: TextView
    private val dotProgress: DotsProgress

    init {
        inflate(context, R.layout.layout_ftl_timer_button, this)

        rlRoot = findViewById(R.id.rl_root)
        progressView = findViewById(R.id.progress_view)
        llContainer = findViewById(R.id.ll_container)
        tvTime = findViewById(R.id.tv_time)
        tvLabel = findViewById(R.id.tv_label)
        dotProgress = findViewById(R.id.dot_progress)

        context.withStyledAttributes(attrs, R.styleable.FTLTimerButton) {
            isTimerRenewableInside = getBoolean(R.styleable.FTLTimerButton_isRenewableInside, true)

            updateViewState(
                getColor(R.styleable.FTLTimerButton_ftlTimerButton_textColor, -1),
                getColor(R.styleable.FTLTimerButton_ftlTimerButton_dotColor, -1),
                getColor(R.styleable.FTLTimerButton_ftlTimerButton_bounceDotColor, -1),
                getColorStateList(R.styleable.FTLTimerButton_ftlTimerButton_textColor)
            )
        }

        llContainer.apply {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (measuredWidth > 0) {
                        progressView.updateLayoutParams {
                            width = measuredWidth
                            height = measuredHeight
                        }
                        viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            })
        }

        super.setOnClickListener { if (!inProgress) clickListener?.onClick(it) }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        updateDotProgressVisibility(inProgress)
    }

    override fun onSaveInstanceState(): Parcelable? =
        SavedState(super.onSaveInstanceState()).apply {
            inProgress = this@FTLTimerButton.inProgress
            estimateDuration = this@FTLTimerButton.estimateDuration
            remainedDuration = this@FTLTimerButton.remainedDuration
            estimateSuccessAt = this@FTLTimerButton.estimateSuccessAt
            textColorRes = this@FTLTimerButton.textColorRes
            dotColorRes = this@FTLTimerButton.dotColorRes
            bounceDotColorRes = this@FTLTimerButton.bounceDotColorRes
            state = this@FTLTimerButton.state
        }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            updateDotProgressVisibility(state.inProgress)
            estimateDuration = state.estimateDuration
            remainedDuration = state.remainedDuration
            estimateSuccessAt = state.estimateSuccessAt
            this.state = state.state
            updateTextColor(state.textColorRes)
            updateDotColor(state.dotColorRes)
            updateBounceDotColor(state.bounceDotColorRes)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        clickListener = l
    }

    private fun launchTimerTask(value: Long) {
        remainedDuration = value
        timer?.cancel()
        timer = timer("Timer", false, 0L, 1000L) {
            Handler(Looper.getMainLooper()).post {
                remainedDuration -= 1000L
                if (estimateDuration != 0L) {
                    progressView.progress = remainedDuration * 100f / estimateDuration
                }
            }
        }
    }

    fun updateRemainedDuration(remDuration: Long) {
        remainedDuration = remDuration
        if (estimateDuration != 0L) {
            progressView.progress = remainedDuration * 100f / estimateDuration
        }
    }

    fun updateTextColor(@ColorRes colorRes: Int) {
        textColorRes = colorRes
        if (textColorRes != -1) {
            val colorStateList = ContextCompat.getColorStateList(context, textColorRes)
            if (colorStateList != null) {
                tvTime.setTextColor(colorStateList)
                tvLabel.setTextColor(colorStateList)
            } else {
                tvTime.setTextColor(ContextCompat.getColor(context, textColorRes))
                tvLabel.setTextColor(ContextCompat.getColor(context, textColorRes))
            }
        } else {
            setTextColorFromState()
        }
    }

    fun updateDotColor(@ColorRes colorRes: Int) {
        dotColorRes = colorRes
        dotProgress.dotColor = ContextCompat.getColor(
            context,
            if (dotColorRes != -1) dotColorRes else state.dotColor
        )
    }

    fun updateBounceDotColor(@ColorRes colorRes: Int) {
        bounceDotColorRes = colorRes
        dotProgress.dotColor = ContextCompat.getColor(
            context,
            if (bounceDotColorRes != -1) bounceDotColorRes else state.bounceDotColor
        )
    }

    private fun updateViewState(
        @ColorInt textColor: Int = -1,
        @ColorInt dotColor: Int = -1,
        @ColorInt bounceDotColor: Int = -1,
        texColorStateList: ColorStateList? = null
    ) {
        TransitionManager.beginDelayedTransition(rlRoot, fadeTransition)

        with(dotProgress) {
            isVisible = inProgress

            this.dotColor = if (dotColor != -1) {
                dotColor
            } else {
                ContextCompat.getColor(context, state.dotColor)
            }

            this.bounceDotColor = if (bounceDotColor != -1) {
                bounceDotColor
            } else {
                ContextCompat.getColor(context, state.bounceDotColor)
            }
        }

        with(progressView) {
            colorBackground = ContextCompat.getColor(context, state.progressBgColor)
            colorProgress = ContextCompat.getColor(context, state.progressColor)
        }

        when {
            texColorStateList != null -> {
                tvTime.setTextColor(texColorStateList)
                tvLabel.setTextColor(texColorStateList)
            }
            textColor != -1 -> {
                tvTime.setTextColor(textColor)
                tvLabel.setTextColor(textColor)
            }
            else -> setTextColorFromState()
        }
        tvLabel.setText(state.text)


        background = configureSelector(
            ContextCompat.getColor(context, state.progressColor),
            floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
        )

        llContainer.background = configureSelector(
            ContextCompat.getColor(context, state.progressColor),
            floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
        )
    }

    private fun configureSelector(@ColorInt color: Int, radii: FloatArray) =
        StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_pressed), GradientDrawable().apply {
                setColor(color)
                cornerRadii = radii
            })
            addState(intArrayOf(), GradientDrawable().apply {
                setColor(Color.TRANSPARENT)
                cornerRadii = radii
            })
        }

    fun updateDotProgressVisibility(isVisible: Boolean) {
        TransitionManager.beginDelayedTransition(rlRoot, fadeTransition)

        inProgress = isVisible
        llContainer.isVisible = !isVisible

        with(dotProgress) {
            if (isVisible) startAnimation()
            else stopAnimation()
            this.isVisible = inProgress
        }
    }

    fun updateState(state: State) {
        clearCustomColors()
        this.state = state
        updateViewState()
    }

    private fun clearCustomColors() {
        textColorRes = -1
        dotColorRes = -1
        bounceDotColorRes = -1
    }

    private fun setTextColorFromState() {
        val color = ContextCompat.getColor(context, state.textColor)

        if (color < 0) {
            tvTime.setTextColor(ContextCompat.getColorStateList(context, state.textColor))
            tvLabel.setTextColor(ContextCompat.getColorStateList(context, state.textColor))
        } else {
            tvTime.setTextColor(color)
            tvLabel.setTextColor(color)
        }
    }

    internal class SavedState : BaseSavedState {
        var state = State.NEW
        var inProgress = false
        var estimateDuration: Long = 0L
        var remainedDuration: Long = 0L
        var estimateSuccessAt: String? = null

        @ColorRes
        var textColorRes = -1

        @ColorRes
        var dotColorRes = -1

        @ColorRes
        var bounceDotColorRes = -1

        constructor(superState: Parcelable?) : super(superState)

        constructor(parcel: Parcel) : super(parcel) {
            state = State.values()[parcel.readInt()]
            inProgress = parcel.readByte() == 1.toByte()
            estimateDuration = parcel.readLong()
            remainedDuration = parcel.readLong()
            estimateSuccessAt = parcel.readString()
            textColorRes = parcel.readInt()
            dotColorRes = parcel.readInt()
            bounceDotColorRes = parcel.readInt()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)
            parcel.writeInt(state.ordinal)
            parcel.writeByte(if (inProgress) 1 else 0)
            parcel.writeLong(estimateDuration)
            parcel.writeLong(remainedDuration)
            parcel.writeString(estimateSuccessAt)
            parcel.writeInt(textColorRes)
            parcel.writeInt(dotColorRes)
            parcel.writeInt(bounceDotColorRes)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}