package com.foodtechlab.ftlandroiduikit.textfield.timer

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.updateLayoutParams
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.time.FTLDeliveryTimeView
import com.foodtechlab.ftlandroiduikit.common.ProgressView
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.Size
import kotlin.concurrent.timer

class FTLDeliveryTimerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : RelativeLayout(context, attrs, defStyleAttrs) {

    private val displayDensity = resources.displayMetrics.density

    private var size: Size
        get() = ftlDeliveryTime.size
        set(value) {
            ftlDeliveryTime.size = value
        }

    private val progressView: ProgressView
    private val ftlDeliveryTime: FTLDeliveryTimeView

    init {
        View.inflate(context, R.layout.layout_ftl_delivery_timer_view, this)

        progressView = findViewById(R.id.progress_view)
        ftlDeliveryTime = findViewById(R.id.ftl_delivery_time)

        ftlDeliveryTime.apply {
            deliveryTimeMillis = remainedTimeMillis
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (measuredWidth > 0) {
                        progressView.updateLayoutParams {
                            width = measuredWidth
                            height = measuredHeight
                        }
                        progressView.autoAnimate = false
                        if (!progressView.isFilled) {
                            progressView.progress = progressView.max
                        }
                        viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            })
        }

        context.withStyledAttributes(attrs, R.styleable.FTLDeliveryTimerView) {
            val ordinal = getInt(
                R.styleable.FTLDeliveryTimerView_deliveryTimerView_size,
                Size.SMALL.ordinal
            )
            size = Size.values()[ordinal]

            progressView.radius = size.radius * displayDensity
        }
    }

    fun setRemainedTime(time: Long) {
        with(ftlDeliveryTime) {
            remainedTimeMillis = time

            timer("Delivery Timer", false, 0L, 1000L) {
                if (!progressView.autoAnimate) {
                    progressView.autoAnimate = true
                }

                Handler(Looper.getMainLooper()).post {
                    remainedTimeMillis -= 1000L
                    progressView.progress =
                        (remainedTimeMillis * 100 / initialRemainedTimeMillis).toFloat()
                }
            }
        }
    }
}