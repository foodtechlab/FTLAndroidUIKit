package com.foodtechlab.ftlandroiduikit.textfield.timer

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.updateLayoutParams
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.common.ProgressView
import com.foodtechlab.ftlandroiduikit.textfield.time.FTLDeliveryTimeView
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.Size
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.getMillis
import java.util.*
import kotlin.concurrent.timer

class FTLDeliveryTimerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : RelativeLayout(context, attrs, defStyleAttrs) {

    private val displayDensity = resources.displayMetrics.density

    private var textSize: Float
        get() = ftlDeliveryTime.textSize
        set(value) {
            ftlDeliveryTime.textSize = value
        }

    var timeZoneId: String? = null

    var autoAnimateProgress: Boolean
        get() = progressView.autoAnimate
        set(value) {
            progressView.autoAnimate = value
        }

    var max: Float
        get() = progressView.max
        set(value) {
            progressView.max = value
        }

    var estimateDuration: Long
        get() = ftlDeliveryTime.estimateDuration
        set(value) {
            ftlDeliveryTime.estimateDuration = value
        }

    var remainedDuration: Long
        get() = ftlDeliveryTime.remainedDuration
        set(value) {
            with(ftlDeliveryTime) {
                remainedDuration = value
                timer?.cancel()
                timer = timer("Delivery Timer", false, 0L, 1000L) {
                    Handler(Looper.getMainLooper()).post {
                        remainedDuration -= 1000L
                        if (estimateDuration != 0L) {
                            progressView.progress = remainedDuration * 100f / estimateDuration
                        }
                    }
                }
            }
        }

    var estimateSuccessAt: String? = null
        set(value) {
            field = value
            remainedDuration = getMillis(value, timeZoneId) - System.currentTimeMillis()
        }

    private var size: Size
        get() = ftlDeliveryTime.size
        set(value) {
            ftlDeliveryTime.size = value
        }

    private var timer: Timer? = null

    private val progressView: ProgressView
    private val ftlDeliveryTime: FTLDeliveryTimeView

    init {
        inflate(context, R.layout.layout_ftl_delivery_timer_view, this)

        progressView = findViewById(R.id.progress_view)
        ftlDeliveryTime = findViewById(R.id.ftl_delivery_time)

        ftlDeliveryTime.deliveryTimeMillis = remainedDuration

        context.withStyledAttributes(attrs, R.styleable.FTLDeliveryTimerView) {
            val ordinal = getInt(
                R.styleable.FTLDeliveryTimerView_deliveryTimerView_size,
                Size.SMALL.ordinal
            )
            size = Size.values()[ordinal]

            textSize = getDimension(
                R.styleable.FTLDeliveryTimerView_timerView_textSize,
                size.textSize * displayDensity
            )

            progressView.radius = size.radius * displayDensity
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        progressView.updateLayoutParams {
            width = measuredWidth
            height = measuredHeight
        }

        ftlDeliveryTime.updateLayoutParams {
            width = measuredWidth
            height = measuredHeight
        }
    }

    override fun onSaveInstanceState(): Parcelable? =
        SavedState(super.onSaveInstanceState()).apply {
            estimateDuration = this@FTLDeliveryTimerView.estimateDuration
            remainedDuration = this@FTLDeliveryTimerView.remainedDuration
            estimateSuccessAt = this@FTLDeliveryTimerView.estimateSuccessAt
        }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            estimateDuration = state.estimateDuration
            remainedDuration = state.remainedDuration
            estimateSuccessAt = state.estimateSuccessAt
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    internal class SavedState : BaseSavedState {
        var estimateDuration: Long = 0L
        var remainedDuration: Long = 0L
        var estimateSuccessAt: String? = null

        constructor(superState: Parcelable?) : super(superState)

        constructor(parcel: Parcel) : super(parcel) {
            estimateDuration = parcel.readLong()
            remainedDuration = parcel.readLong()
            estimateSuccessAt = parcel.readString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)
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