package com.foodtechlab.ftlandroiduikit.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.math.floor

class DotsLine
@JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    private var dotRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, resources.displayMetrics)
    private var dotGap = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, resources.displayMetrics)
    private val paint: Paint = Paint()

    init {
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.flags = Paint.ANTI_ALIAS_FLAG
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = resolveSize((paddingLeft + paddingRight + 2 * dotRadius).toInt(), widthMeasureSpec)
        val height = resolveSize(paddingTop + paddingBottom + suggestedMinimumHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        val height = (canvas.height - paddingTop - paddingBottom).toFloat()
        val diameter = 2 * dotRadius
        val dotsCount: Int = floor(((height - diameter) / (diameter + dotGap)).toDouble()).toInt()
        for (i in 0..dotsCount) {
            canvas.drawCircle(paddingLeft + dotRadius,
                paddingTop + dotRadius + i * (diameter + (height - (diameter * (dotsCount + 1))) / dotsCount),
                dotRadius,
                paint)
        }
    }
}