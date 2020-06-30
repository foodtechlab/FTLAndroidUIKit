package com.foodtechlab.ftlandroiduikit.button.timer

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Handler
import android.os.Looper
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
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

    private var timer: Timer? = null

    var timeZoneId: String? = null

    var inProgress = false
        private set

    var state: State = State.ORDER_MAKE
        set(value) {
            field = value
            updateViewState()
        }

    var deliveryTime: String? = if (isInEditMode) "00:00" else null
        set(value) {
            field = value
            tvTime.text = value
        }

    private var remainedDuration: Long = 0L
        private set(value) {
            field = value

            val hours = TimeUnit.MILLISECONDS.toHours(value)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(value) % 60 + hours * 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(value - minutes * 60 * 1000) % 3600

            if (seconds >= 0) {
                Log.d(
                    "timer_test",
                    "estimateSuccessAt = $estimateSuccessAt cutTime = ${System.currentTimeMillis()} hh:mm:ss = $hours:$minutes:$seconds"
                )
            }

            val format = if (seconds < 0) R.string.format_negative_mm_ss else R.string.format_mm_ss

            deliveryTime = String.format(context.getString(format), abs(minutes), abs(seconds))
        }

    var estimateDuration = 0L

    var estimateSuccessAt: String? = null
        set(value) {
            field = value
            updateRemainedDuration(getMillis(value, timeZoneId) - System.currentTimeMillis())
        }

    private val rlContainer: RelativeLayout
    private val llContainer: LinearLayout
    private val progressView: ProgressView
    private val tvTime: TextView
    private val tvLabel: TextView
    private val dotProgress: DotsProgress

    init {
        inflate(context, R.layout.layout_ftl_timer_button, this)

        progressView = findViewById(R.id.progress_view)
        llContainer = findViewById(R.id.ll_container)
        rlContainer = findViewById(R.id.rl_container)
        tvTime = findViewById(R.id.tv_time)
        tvLabel = findViewById(R.id.tv_label)
        dotProgress = findViewById(R.id.dot_progress)

        updateViewState()

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
    }

    override fun onSaveInstanceState(): Parcelable? =
        SavedState(super.onSaveInstanceState()).apply {
            inProgress = this@FTLTimerButton.inProgress
            estimateDuration = this@FTLTimerButton.estimateDuration
            remainedDuration = this@FTLTimerButton.remainedDuration
            estimateSuccessAt = this@FTLTimerButton.estimateSuccessAt
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
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    private fun updateRemainedDuration(value: Long) {
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

    private fun updateViewState() {
        with(dotProgress) {
            isVisible = inProgress
            dotColor = ContextCompat.getColor(context, state.dotColor)
            bounceDotColor = ContextCompat.getColor(context, state.bounceDotColor)
        }

        with(progressView) {
            colorBackground = ContextCompat.getColor(context, state.progressBgColor)
            colorProgress = ContextCompat.getColor(context, state.progressColor)
        }

        tvTime.setTextColor(ContextCompat.getColor(context, state.textColor))

        with(tvLabel) {
            setTextColor(ContextCompat.getColor(context, state.textColor))
            setText(state.text)
        }

        rlContainer.background = configureSelector(
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
        inProgress = isVisible
        llContainer.isVisible = !isVisible

        with(dotProgress) {
            if (isVisible) startAnimation()
            else stopAnimation()
            this.isVisible = inProgress
        }
    }

    internal class SavedState : BaseSavedState {
        var state = State.ORDER_MAKE
        var inProgress = false
        var estimateDuration: Long = 0L
        var remainedDuration: Long = 0L
        var estimateSuccessAt: String? = null

        constructor(superState: Parcelable?) : super(superState)

        constructor(parcel: Parcel) : super(parcel) {
            state = State.values()[parcel.readInt()]
            inProgress = parcel.readByte() == 1.toByte()
            estimateDuration = parcel.readLong()
            remainedDuration = parcel.readLong()
            estimateSuccessAt = parcel.readString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)
            parcel.writeInt(state.ordinal)
            parcel.writeByte(if (inProgress) 1 else 0)
            parcel.writeLong(estimateDuration)
            parcel.writeLong(remainedDuration)
            parcel.writeString(estimateSuccessAt)
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