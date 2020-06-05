package com.foodtechlab.ftlandroiduikit.textfield.time

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.DeliveryStatus
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.Icon
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.Size
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.formatTime
import kotlin.math.min

class FTLDeliveryTimeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val displayDensity = resources.displayMetrics.density

    var deliveryTime: String? = null
        set(value) {
            field = value
            invalidate()
        }

    var date: String? = null
        set(value) {
            field = value
            deliveryTime = formatTime(value)
        }

    var deliveryTimeMillis: Long = 0L
        set(value) {
            field = value
            deliveryTime = formatTime(value)
        }

    private var paddingHorizontalOrig = 8f * displayDensity

    private var paddingHorizontal = 8f * displayDensity
        set(value) {
            field = value
            paddingHorizontalOrig = value
        }

    private var paddingVerticalOrig = 8f * displayDensity

    private var paddingVertical = 8f * displayDensity
        set(value) {
            field = value
            paddingVerticalOrig = value
        }

    private var textSize = 16f * displayDensity
        set(value) {
            field = value
            textPaint.textSize = value
        }

    private val timeHeight: Int
        get() = deliveryTime?.let {
            val timeRect = Rect()
            textPaint.getTextBounds(it, 0, it.length, timeRect)
            timeRect.height()
        } ?: 0

    private val timeWidth: Int
        get() = deliveryTime?.let {
            val timeRect = Rect()
            textPaint.getTextBounds(it, 0, it.length, timeRect)
            timeRect.width()
        } ?: 0

    var deliveryStatus: DeliveryStatus = DeliveryStatus.USUAL
        set(value) {
            field = value
            updateViewState()
            requestLayout()
        }

    private var size: Size = Size.SMALL

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
        context.withStyledAttributes(attrs, R.styleable.FTLDeliveryTimeView) {
            val sizeOrdinal = getInt(R.styleable.FTLDeliveryTimeView_size, Size.SMALL.ordinal)
            size = Size.values()[sizeOrdinal]

            val statusOrdinal =
                getInt(R.styleable.FTLDeliveryTimeView_status, DeliveryStatus.USUAL.ordinal)
            deliveryStatus = DeliveryStatus.values()[statusOrdinal]

            updateViewState()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBackground()
        canvas.drawIcon()
        canvas.drawTime()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val iconRequiredWidth = icon?.size ?: 0f
        val iconMarginEnd = icon?.marginEnd ?: 0f

        val desiredWidth =
            suggestedMinimumWidth + 2 * paddingHorizontal + timeWidth + iconRequiredWidth + iconMarginEnd
        val desiredHeight =
            suggestedMinimumHeight + 2 * paddingVertical + timeHeight

        setMeasuredDimension(
            measureDimension(desiredWidth.toInt(), widthMeasureSpec),
            measureDimension(desiredHeight.toInt(), heightMeasureSpec)
        )
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        val result = when (specMode) {
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

        if (result < desiredSize) {
            val scale = result.toFloat() / desiredSize
            val textSize = textSize * scale
            textPaint.textSize = min(textPaint.textSize, textSize)

            paddingHorizontal = min(paddingHorizontal, paddingHorizontalOrig * scale)
            paddingVertical = min(paddingVertical, paddingVerticalOrig * scale)

            icon?.let { ic ->
                iconOrig?.let { icOrig ->
                    ic.size = min(ic.size, icOrig.size * scale)
                    ic.marginEnd = min(ic.marginEnd, icOrig.marginEnd * scale)
                }
            }
        }

        return result
    }

    private fun updateViewState() {
        icon = when {
            size == Size.SMALL && deliveryStatus == DeliveryStatus.USUAL -> null
            else -> Icon(
                size.iconSize * displayDensity,
                size.iconMarginEnd * displayDensity,
                deliveryStatus.iconDrawable?.let { ContextCompat.getDrawable(context, it) }
            )
        }
        val radius = size.radius * displayDensity
        bgCorners.forEachIndexed { index, _ ->
            bgCorners[index] = radius
        }

        paddingHorizontal = size.paddingHorizontal * displayDensity
        paddingVertical = size.paddingVertical * displayDensity
        textSize = size.textSize * displayDensity
        textPaint.color = ContextCompat.getColor(context, deliveryStatus.textColor)
        bgPaint.color = ContextCompat.getColor(context, deliveryStatus.bgColor)

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

    companion object {
        private const val HALF = .5f
    }
}