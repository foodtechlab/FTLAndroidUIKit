package com.foodtechlab.ftlandroiduikit.textfield.time

import DeliveryStatus
import android.content.Context
import android.graphics.*
import android.os.Parcel
import android.os.Parcelable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.Icon
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.Size
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.formatTime
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.math.abs
import kotlin.math.min

class FTLDeliveryTimeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val displayDensity = resources.displayMetrics.density

    private var textScale = 1f

    var deliveryTime: String? = if (isInEditMode) "00:00" else null
        set(value) {
            field = value

            val length = value?.length ?: 0
            textScale = when {
                TIME_TEMPLATE.length < length -> TIME_TEMPLATE.length.toFloat() / length
                textSize < size.textSize * displayDensity -> {
                    val tSize = size.textSize * displayDensity
                    textSize / tSize
                }
                else -> 1f
            }

            updateViewState()
            requestLayout()
        }

    var timeZoneId: String? = null

    var date: String? = null
        set(value) {
            field = value
            deliveryTime = formatTime(value, timeZoneId)
        }

    var deliveryTimeMillis = 0L
        set(value) {
            field = value
            deliveryTime = formatTime(value, timeZoneId)
        }

    var estimateDuration = 0L

    var remainedDuration = 0L
        set(value) {
            if (deliveryStatus == DeliveryStatus.IN_PROGRESS || deliveryStatus == DeliveryStatus.IN_PROGRESS_LATE) {
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

                if ((seconds < 0 || minutes < 0) && deliveryStatus != DeliveryStatus.IN_PROGRESS_LATE)
                    deliveryStatus = DeliveryStatus.IN_PROGRESS_LATE
            }
        }

    private var paddingHorizontalOrig = 8f * displayDensity

    private var paddingHorizontal = 8f * displayDensity
        set(value) {
            field = value
            paddingHorizontalOrig = value
        }

    private var marginEndOrig = 0f

    private var marginEnd = 0f
        set(value) {
            field = value
            marginEndOrig = value
        }

    private var paddingVerticalOrig = 8f * displayDensity

    private var paddingVertical = 8f * displayDensity
        set(value) {
            field = value
            paddingVerticalOrig = value
        }

    var textSize = 16f * displayDensity
        set(value) {
            field = value

            val tSize = value * displayDensity
            textScale = textSize / tSize
        }

    private val timeHeight: Int
        get() = deliveryTime?.let {
            textPaint.getTextBounds(it, 0, it.length, timeRect)
            timeRect.height()
        } ?: 0

    private val timeWidth: Int
        get() = deliveryTime?.let {
            textPaint.getTextBounds(it, 0, it.length, timeRect)
            timeRect.width()
        } ?: 0

    private val desiredTimeWidth by lazy(LazyThreadSafetyMode.NONE) {
        val timeRect = Rect()
        val tPaint = TextPaint(TextPaint.ANTI_ALIAS_FLAG).apply {
            textSize = size.textSize * displayDensity
        }
        tPaint.getTextBounds(TIME_TEMPLATE, 0, TIME_TEMPLATE.length, timeRect)
        timeRect.width()
    }

    private val desiredTimeHeight by lazy(LazyThreadSafetyMode.NONE) {
        val timeRect = Rect()
        val tPaint = TextPaint(TextPaint.ANTI_ALIAS_FLAG).apply {
            textSize = size.textSize * displayDensity
        }
        tPaint.getTextBounds(TIME_TEMPLATE, 0, TIME_TEMPLATE.length, timeRect)
        timeRect.height()
    }

    private val timeRect = Rect()

    var deliveryStatus: DeliveryStatus = DeliveryStatus.USUAL
        set(value) {
            field = value
            updateViewState()
            requestLayout()
        }

    var size: Size = Size.SMALL
        set(value) {
            field = value
            textSize = value.textSize * displayDensity
            updateViewState()
            requestLayout()
        }

    private val bgPath = Path()

    private val bgRect = RectF()

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val textPaint = TextPaint(TextPaint.ANTI_ALIAS_FLAG)

    private var iconOrig: Icon? = null

    private var icon: Icon? = null
        set(value) {
            field = value
            iconOrig = value?.copy()
        }

    private val bgCorners = FloatArray(8)

    init {
        isSaveEnabled = true

        context.withStyledAttributes(attrs, R.styleable.FTLDeliveryTimeView) {
            val sizeOrdinal = getInt(R.styleable.FTLDeliveryTimeView_size, Size.SMALL.ordinal)
            size = Size.values()[sizeOrdinal]

            textSize = getDimension(
                R.styleable.FTLDeliveryTimeView_timeView_textSize,
                size.textSize * displayDensity
            )

            val statusOrdinal =
                getInt(R.styleable.FTLDeliveryTimeView_status, DeliveryStatus.USUAL.ordinal)
            deliveryStatus = DeliveryStatus.values()[statusOrdinal]

            val tSize = size.textSize * displayDensity
            textScale = textSize / tSize

            onThemeChanged()
        }
    }

    override fun onSaveInstanceState(): Parcelable =
        SavedState(super.onSaveInstanceState()).apply {
            estimateDuration = this@FTLDeliveryTimeView.estimateDuration
            remainedDuration = this@FTLDeliveryTimeView.remainedDuration
        }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            estimateDuration = state.estimateDuration
            remainedDuration = state.remainedDuration
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBackground()
        canvas.drawIcon()
        canvas.drawTime()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val iconRequiredWidth = icon?.let { it.size + it.marginEnd } ?: 0f

        val desiredWidth =
            suggestedMinimumWidth + 2 * paddingHorizontal + desiredTimeWidth + iconRequiredWidth + marginEnd

        val desiredHeight = suggestedMinimumHeight + 2 * paddingVertical + desiredTimeHeight

        setMeasuredDimension(
            measureDimension(desiredWidth.toInt(), widthMeasureSpec),
            measureDimension(desiredHeight.toInt(), heightMeasureSpec)
        )
    }

    private fun onThemeChanged() {
        DeliveryStatus.USUAL.textColor = FTLDeliveryTimeViewUsualThemeManager().lightTheme.textColor
        DeliveryStatus.USUAL.bgColor = FTLDeliveryTimeViewUsualThemeManager().lightTheme.bgColor
        DeliveryStatus.USUAL.iconColor = FTLDeliveryTimeViewUsualThemeManager().lightTheme.iconColor
        launch {
            val usualTheme = FTLDeliveryTimeViewUsualThemeManager().mapToViewData()
            val urgentTheme = FTLDeliveryTimeViewUrgentThemeManager().mapToViewData()
            val deliveredTheme = FTLDeliveryTimeViewDeliveredThemeManager().mapToViewData()
            val deliveredLateTheme = FTLDeliveryTimeViewDeliveredLateThemeManager().mapToViewData()
            val canceledTheme = FTLDeliveryTimeViewCanceledThemeManager().mapToViewData()
            val progressTheme = FTLDeliveryTimeViewInProgressThemeManager().mapToViewData()
            val progressLateTheme = FTLDeliveryTimeViewInProgressLateThemeManager().mapToViewData()

            usualTheme.zip(urgentTheme) { usual, urgent ->
                DeliveryStatus.USUAL.textColor = usual.textColor
                DeliveryStatus.USUAL.bgColor = usual.bgColor
                DeliveryStatus.USUAL.iconColor = usual.iconColor
                urgent
            }.zip(deliveredTheme) { urgent, delivered ->
                DeliveryStatus.URGENT.textColor = urgent.textColor
                DeliveryStatus.URGENT.bgColor = urgent.bgColor
                DeliveryStatus.URGENT.iconColor = urgent.iconColor
                delivered
            }.zip(deliveredLateTheme) { delivered, deliveredLate ->
                DeliveryStatus.DELIVERED.textColor =
                    delivered.textColor
                DeliveryStatus.DELIVERED.bgColor = delivered.bgColor
                DeliveryStatus.DELIVERED.iconColor = delivered.iconColor
                deliveredLate
            }.zip(canceledTheme) { deliveredLate, canceled ->
                DeliveryStatus.DELIVERED_LATE.textColor =
                    deliveredLate.textColor
                DeliveryStatus.DELIVERED_LATE.bgColor =
                    deliveredLate.bgColor
                DeliveryStatus.DELIVERED_LATE.iconColor =
                    deliveredLate.iconColor
                canceled
            }.zip(progressTheme) { canceled, progress ->
                DeliveryStatus.CANCELED.textColor = canceled.textColor
                DeliveryStatus.CANCELED.bgColor = canceled.bgColor
                DeliveryStatus.CANCELED.iconColor = canceled.iconColor
                progress
            }.zip(progressLateTheme) { progress, progressLate ->
                DeliveryStatus.IN_PROGRESS.textColor =
                    progress.textColor
                DeliveryStatus.IN_PROGRESS.bgColor =
                    progress.bgColor
                DeliveryStatus.IN_PROGRESS.iconColor =
                    progress.iconColor
                progressLate
            }.collect { progressLate ->
                DeliveryStatus.IN_PROGRESS_LATE.textColor =
                    progressLate.textColor
                DeliveryStatus.IN_PROGRESS_LATE.bgColor =
                    progressLate.bgColor
                DeliveryStatus.IN_PROGRESS_LATE.iconColor =
                    progressLate.iconColor
                updateViewState()
            }
        }
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        return when (specMode) {
            MeasureSpec.EXACTLY -> specSize
            else -> {
                var tempRes = desiredSize
                if (specMode == MeasureSpec.AT_MOST) {
                    tempRes = min(tempRes, specSize)
                }
                if (tempRes < desiredSize) {
                    Log.e("FTLDeliveryTimeView", "The view is too small, the content might get cut")
                }
                tempRes
            }
        }
    }

    private fun updateViewState() {
        icon = when {
            size == Size.SMALL && deliveryStatus == DeliveryStatus.USUAL -> null
            else -> deliveryStatus.iconDrawable?.let {
                Icon(
                    size.iconSize * displayDensity,
                    size.iconMarginEnd * displayDensity,
                    deliveryStatus.iconDrawable?.let { ContextCompat.getDrawable(context, it) }
                )
            }
        }
        val radius = size.radius * displayDensity
        bgCorners.forEachIndexed { index, _ ->
            bgCorners[index] = radius
        }

        marginEnd = size.marginEnd * displayDensity
        paddingHorizontal = size.paddingHorizontal * displayDensity
        paddingVertical = size.paddingVertical * displayDensity
        textPaint.textSize = textSize * textScale

        deliveryStatus.textColor?.let {
            textPaint.color = ContextCompat.getColor(context, it)
        }
        deliveryStatus.bgColor?.let {
            bgPaint.color = ContextCompat.getColor(context, it)
        }

        if (!isInEditMode) textPaint.typeface = ResourcesCompat.getFont(context, size.font)
    }

    private fun Canvas.drawBackground() {
        bgPath.reset()
        bgRect.set(0f, 0f, width.toFloat(), height.toFloat())
        bgPath.addRoundRect(bgRect, bgCorners, Path.Direction.CW)
        drawPath(bgPath, bgPaint)
    }

    private fun Canvas.drawTime() {
        deliveryTime?.let { time ->
            val iconRequiredWidth = icon?.size ?: 0f
            val iconMarginEnd = icon?.marginEnd ?: 0f

            val posX = (width * HALF - timeWidth * HALF).coerceAtLeast(
                iconRequiredWidth + paddingHorizontal + iconMarginEnd
            )
            val posY = height * HALF + timeHeight * HALF

            drawText(time, posX, posY, textPaint)
        }
    }

    private fun Canvas.drawIcon() {
        icon?.let { ic ->
            ic.drawable?.apply {
                val left = paddingHorizontal
                val top = height * HALF - ic.size * HALF
                val right = paddingHorizontal + ic.size
                val bottom = height * HALF + ic.size * HALF
                setBounds(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
                draw(this@drawIcon)
            }
        }
    }

    internal class SavedState : BaseSavedState {
        var estimateDuration = 0L

        var remainedDuration: Long = 0L

        constructor(source: Parcel) : super(source) {
            estimateDuration = source.readLong()
            remainedDuration = source.readLong()
        }

        constructor(superState: Parcelable?) : super(superState)

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeLong(estimateDuration)
            out.writeLong(remainedDuration)
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

    companion object {
        private const val HALF = .5f

        private const val TIME_TEMPLATE = "-00:00"
    }
}

data class FTLDeliveryTimeViewTheme(
    @ColorRes val textColor: Int,
    @ColorRes val bgColor: Int,
    @ColorRes val iconColor: Int? = null
) : ViewTheme()
