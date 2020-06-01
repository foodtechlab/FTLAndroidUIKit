package com.foodtechlab.ftlandroiduikit.textfield.time

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.DeliveryMode
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.Icon
import com.foodtechlab.ftlandroiduikit.textfield.time.helper.formatTime
import kotlin.math.min

open class DeliveryTimeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val displayDensity = resources.displayMetrics.density

    @ColorRes
    protected var urgentTimeColor = R.color.OnPrimary

    @ColorRes
    protected var defaultTextColor = R.color.OnBackgroundPrimary

    @ColorRes
    protected var urgentBgColor = R.color.AdditionalPink

    @ColorRes
    protected var defaultBgColor = R.color.AdditionalLightGreen

    @FontRes
    protected var font = R.font.roboto_bold

    private var deliveryTime: String = "77:55"

    var deliveryTimeMillis: Long = 0L
        set(value) {
            field = value
            deliveryTime = formatTime(value)
            invalidate()
        }

    protected var cornerRadius = 16f
        set(value) {
            field = value
            bgCorners.forEachIndexed { index, _ ->
                bgCorners[index] = value * displayDensity
            }
        }

    protected var paddingStart = 8f
    protected var paddingEnd = 8f
    protected var verticalPadding = 8f

    protected var timeSize = 16f

    private val timeHeight: Int
        get() {
            val timeRect = Rect()
            timePaint.getTextBounds(deliveryTime, 0, deliveryTime.length, timeRect)
            return timeRect.height()
        }

    private val timeWidth: Int
        get() {
            val timeRect = Rect()
            timePaint.getTextBounds(deliveryTime, 0, deliveryTime.length, timeRect)
            return timeRect.width()
        }

    var deliveryMode: DeliveryMode = DeliveryMode.USUAL
        set(value) {
            field = value
            updateColors()
            requestLayout()
        }

    private val bgPath = Path()

    private val bgRect = RectF()

    private val bgPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = ContextCompat.getColor(context, defaultBgColor)
            if (!isInEditMode) {
                typeface = ResourcesCompat.getFont(context, font)
            }
        }
    }

    private val timePaint by lazy {
        TextPaint(TextPaint.ANTI_ALIAS_FLAG).apply {
            color = ContextCompat.getColor(context, defaultTextColor)
            textSize = timeSize * displayDensity
            if (!isInEditMode) {
                typeface = ResourcesCompat.getFont(context, font)
            }
        }
    }

    private var iconUrgentOrig: Icon? = null

    private var iconUsualOrig: Icon? = Icon(
        10f,
        16f,
        7f,
        3f,
        ContextCompat.getDrawable(context, R.drawable.ic_flash_small)
    )

    protected var iconUsual: Icon? = null
        set(value) {
            field = value
            iconUsualOrig = value?.copy()
        }

    protected var iconUrgent: Icon? = Icon(
        10f,
        16f,
        7f,
        3f,
        ContextCompat.getDrawable(context, R.drawable.ic_flash_small)
    )
        set(value) {
            field = value
            iconUrgentOrig = value?.copy()
        }

    private val bgCorners = floatArrayOf(
        cornerRadius * displayDensity,
        cornerRadius * displayDensity, // top left corner radius in dp
        cornerRadius * displayDensity,
        cornerRadius * displayDensity, // top right corner radius in dp
        cornerRadius * displayDensity,
        cornerRadius * displayDensity, // bottom right corner radius in dp
        cornerRadius * displayDensity,
        cornerRadius * displayDensity  // bottom left corner radius in dp
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBackground()
        canvas.drawIcon()
        canvas.drawTime()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val icon = if (deliveryMode == DeliveryMode.URGENT) iconUrgent else iconUsual
        val iconRequiredWidth = icon?.let {
            (it.width + it.marginEnd + it.marginStart) * displayDensity
        } ?: 0f

        val desiredWidth =
            suggestedMinimumWidth + (paddingStart + paddingEnd) * displayDensity + timeWidth + iconRequiredWidth
        val desiredHeight =
            suggestedMinimumHeight + 2 * verticalPadding * displayDensity + timeHeight

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
                    Log.e("DeliveryTimeView", "The view is too small, the content might get cut")
                }
                tempRes
            }
        }

        if (result < desiredSize) {
            val scale = result.toFloat() / desiredSize
            val textSize = timeSize * scale
            timePaint.textSize = min(timePaint.textSize, textSize)

            iconUsual?.let { ic ->
                iconUsualOrig?.let { icOrig ->
                    ic.width = min(ic.width, icOrig.width * scale)
                    ic.height = min(ic.height, icOrig.height * scale)
                    ic.marginEnd = min(ic.marginEnd, icOrig.marginEnd * scale)
                    ic.marginStart = min(ic.marginStart, icOrig.marginStart * scale)
                }
            }

            iconUrgent?.let { ic ->
                iconUrgentOrig?.let { icOrig ->
                    ic.width = min(ic.width, icOrig.width * scale)
                    ic.height = min(ic.height, icOrig.height * scale)
                    ic.marginEnd = min(ic.marginEnd, icOrig.marginEnd * scale)
                    ic.marginStart = min(ic.marginStart, icOrig.marginStart * scale)
                }
            }

            Log.v("test_test", "$scale")
        }

        return result
    }

    private fun Canvas.drawBackground() {
        bgPath.reset()
        bgRect.set(0f, 0f, width.toFloat(), height.toFloat())
        bgPath.addRoundRect(bgRect, bgCorners, Path.Direction.CW)
        drawPath(bgPath, bgPaint)
    }

    private fun Canvas.drawTime() {
        val icon = if (deliveryMode == DeliveryMode.URGENT) iconUrgent else iconUsual
        val iconRequiredWidth = icon?.let {
            (it.width + it.marginEnd + it.marginStart) * displayDensity
        } ?: 0f

        val posX = (width * HALF - timeWidth * HALF).coerceAtLeast(
            iconRequiredWidth + paddingStart * displayDensity
        )
        val posY = height * HALF + timeHeight * HALF

        drawText(deliveryTime, posX, posY, timePaint)
    }

    private fun Canvas.drawIcon() {
        val icon = if (deliveryMode == DeliveryMode.URGENT) iconUrgent else iconUsual
        icon?.let { ic ->
            ic.drawable?.apply {
                val left = (paddingStart + ic.marginStart) * displayDensity
                val top = height * HALF - ic.height * displayDensity * HALF
                val right = (paddingStart + ic.width + ic.marginStart) * displayDensity
                val bottom = height * HALF + ic.height * displayDensity * HALF
                setBounds(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
                draw(this@drawIcon)
            }
        }
    }

    private fun updateColors() {
        timePaint.color =
            ContextCompat.getColor(
                context,
                if (deliveryMode == DeliveryMode.URGENT) urgentTimeColor else defaultTextColor
            )
        bgPaint.color =
            ContextCompat.getColor(
                context,
                if (deliveryMode == DeliveryMode.URGENT) urgentBgColor else defaultBgColor
            )
    }

    companion object {
        private const val HALF = .5f
    }
}