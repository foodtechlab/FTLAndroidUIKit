package com.foodtechlab.ftlandroiduikit.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import kotlin.math.floor

class DotsLine @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var dotRadius = context.dpToPx(2f)
    private val paint = Paint()

    @ColorInt
    var lineColor = Color.WHITE
        set(value) {
            field = value
            paint.color = field
            invalidate()
        }

    init {
        paint.color = lineColor
        paint.style = Paint.Style.FILL
        paint.flags = Paint.ANTI_ALIAS_FLAG
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width =
            resolveSize((paddingLeft + paddingRight + 4 * dotRadius).toInt(), widthMeasureSpec)
        val height =
            resolveSize(paddingTop + paddingBottom + suggestedMinimumHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        val height = (height - paddingTop - paddingBottom).toFloat()
        val diameter = 2 * dotRadius
        val dotsCount: Int =
            if (floor(((height - diameter) / (diameter * 2)).toDouble()).toInt() <= 0) {
                floor(height / diameter).toInt()
            } else {
                floor(((height - diameter) / (diameter * 2)).toDouble()).toInt()
            }
        if (dotsCount == 1) {
            canvas.drawCircle(
                paddingLeft + dotRadius,
                height / 2,
                dotRadius,
                paint
            )
        } else {
            for (i in 0..dotsCount) {
                canvas.drawCircle(
                    paddingLeft + dotRadius,
                    paddingTop + dotRadius + i * (diameter + (height - (diameter * dotsCount + 1)) / dotsCount) + diameter,
                    dotRadius,
                    paint
                )
            }
        }
    }
}