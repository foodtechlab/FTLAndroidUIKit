package com.foodtechlab.ftlandroiduikit.imageview.placeholder

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext
import kotlin.math.min
import kotlin.math.pow

class FTLPlaceholderImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr),
    CoroutineScope {
    private val viewThemeManager: ViewThemeManager<FTLPlaceholderImageViewTheme> =
        FTLPlaceholderImageViewThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var borderRadius = 0f
    private var drawableRadius = 0f

    private var initialized = false
    private var rebuildShader = false
    private var drawableDirty = false

    private var borderOverlay = false
        set(value) {
            if (field == value) return
            field = value
            updateDimensions()
            invalidate()
        }

    private var disableCircularTransformation = false
        set(value) {
            if (field == value) return
            field = value
            if (value) {
                bitmap = null
                bitmapCanvas = null
                bitmapPaint.shader = null
            } else {
                initializeBitmap()
            }
            invalidate()
        }

    var imgAlpha: Int = DEFAULT_IMAGE_ALPHA
        set(value) {
            val tempAlpha = value and 0xFF
            if (tempAlpha == field) return
            field = tempAlpha

            // This might be called during ImageView construction before
            // member initialization has finished on API level >= 16.
            if (initialized) {
                bitmapPaint.alpha = tempAlpha
                placeholderBitmapPaint.alpha = tempAlpha
                invalidate()
            }
        }

    var borderWidth = DEFAULT_BORDER_WIDTH
        set(value) {
            if (field == value) return
            field = value
            borderPaint.strokeWidth = borderWidth.toFloat()
            updateDimensions()
            invalidate()
        }

    @ColorInt
    var borderColor: Int = DEFAULT_BORDER_COLOR
        set(value) {
            if (field == value) return
            field = value
            borderPaint.color = value
            invalidate()
        }

    private var placeholder: Drawable? = null
        set(value) {
            field = value
            invalidate()
        }

    private val bitmapPaint = Paint().apply {
        isAntiAlias = true
        isDither = true
        isFilterBitmap = true
        alpha = imgAlpha
        colorFilter = this@FTLPlaceholderImageView.colorFilter
    }

    private val placeholderBitmapPaint = Paint().apply {
        isAntiAlias = true
        isDither = true
        isFilterBitmap = true
        alpha = imgAlpha
        colorFilter = this@FTLPlaceholderImageView.colorFilter
    }

    private val borderPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = borderColor
        strokeWidth = borderWidth.toFloat()
    }

    private val drawableRect = RectF()
    private val borderRect = RectF()

    private val shaderMatrix = Matrix()

    private var bitmap: Bitmap? = null
    private var bitmapCanvas: Canvas? = null

    private var bitmapPlaceholder: Bitmap? = null

    private var colorFilter: ColorFilter? = null

    init {
        context.withStyledAttributes(attrs, R.styleable.FTLPlaceholderImageView) {

            setupPlaceholder()

            borderWidth = getDimensionPixelSize(
                R.styleable.FTLPlaceholderImageView_border_width,
                DEFAULT_BORDER_WIDTH
            )

            borderColor =
                getColor(R.styleable.FTLPlaceholderImageView_border_color, DEFAULT_BORDER_COLOR)

            borderOverlay = getBoolean(
                R.styleable.FTLPlaceholderImageView_border_overlay,
                DEFAULT_BORDER_OVERLAY
            )
        }

        initialized = true

        super.setScaleType(SCALE_TYPE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outlineProvider = OutlineProvider()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                onThemeChanged(theme)
            }
        }
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }

    fun onThemeChanged(theme: FTLPlaceholderImageViewTheme) {
        bitmapPlaceholder = null
        placeholder = ContextCompat.getDrawable(
            context,
            theme.placeholder
        )
    }

    private fun TypedArray.setupPlaceholder() {
        viewThemeManager.lightTheme.placeholder = getResourceId(
            R.styleable.FTLPlaceholderImageView_placeholder_light,
            R.drawable.ic_restaurant_placeholder_light
        )

        viewThemeManager.darkTheme?.placeholder = getResourceId(
            R.styleable.FTLPlaceholderImageView_placeholder_dark,
            R.drawable.ic_restaurant_placeholder_dark
        )
        launch {
            viewThemeManager.mapToViewData().collect { theme ->
                placeholder = ContextCompat.getDrawable(
                    context,
                    theme.placeholder
                )
                try {
                    this.cancel()
                } catch (e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            }
        }
    }

    override fun setScaleType(scaleType: ScaleType) {
        require(scaleType == SCALE_TYPE) {
            String.format("ScaleType %s not supported.", scaleType)
        }
    }

    override fun setAdjustViewBounds(adjustViewBounds: Boolean) {
        require(!adjustViewBounds) { "adjustViewBounds not supported." }
    }

    @SuppressLint("CanvasSize")
    override fun onDraw(canvas: Canvas) {
        if (disableCircularTransformation) {
            super.onDraw(canvas)
            return
        }

        if (bitmap != null) {
            if (drawableDirty && bitmapCanvas != null) {
                drawableDirty = false
                val drawable = drawable
                drawable.setBounds(0, 0, bitmapCanvas!!.width, bitmapCanvas!!.height)
                drawable.draw(bitmapCanvas!!)
            }
            if (rebuildShader) {
                rebuildShader = false
                val bitmapShader =
                    BitmapShader(bitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                bitmapShader.setLocalMatrix(shaderMatrix)
                bitmapPaint.shader = bitmapShader
            }
            canvas.drawCircle(
                drawableRect.centerX(),
                drawableRect.centerY(),
                drawableRadius,
                bitmapPaint
            )
        } else {
            if (bitmapPlaceholder == null) initializePlaceholderBitmap()

            if (drawableDirty && bitmapCanvas != null) {
                drawableDirty = false
                val drawable = placeholder
                drawable?.setBounds(
                    0,
                    0,
                    bitmapCanvas!!.width,
                    bitmapCanvas!!.height
                )
                drawable?.draw(bitmapCanvas!!)
            }
            if (rebuildShader) {
                rebuildShader = false
                val bitmapShader =
                    BitmapShader(bitmapPlaceholder!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                bitmapShader.setLocalMatrix(shaderMatrix)
                placeholderBitmapPaint.shader = bitmapShader
            }
            canvas.drawCircle(
                drawableRect.centerX(),
                drawableRect.centerY(),
                drawableRadius,
                placeholderBitmapPaint
            )
        }

        if (borderWidth > 0) {
            canvas.drawCircle(
                borderRect.centerX(),
                borderRect.centerY(),
                borderRadius,
                borderPaint
            )
        }
    }

    override fun invalidateDrawable(dr: Drawable) {
        drawableDirty = true
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateDimensions()
        invalidate()
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        updateDimensions()
        invalidate()
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        super.setPaddingRelative(start, top, end, bottom)
        updateDimensions()
        invalidate()
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        initializeBitmap()
        invalidate()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        initializeBitmap()
        invalidate()
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        initializeBitmap()
        invalidate()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        initializeBitmap()
        invalidate()
    }

    override fun setColorFilter(cf: ColorFilter) {
        if (cf === colorFilter) return

        colorFilter = cf

        // This might be called during ImageView construction before
        // member initialization has finished on API level <= 19.
        if (initialized) {
            bitmapPaint.colorFilter = cf
            placeholderBitmapPaint.colorFilter = cf
            invalidate()
        }
    }

    override fun getColorFilter(): ColorFilter? {
        return colorFilter
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) return null

        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else try {
            val bitmap = when (drawable) {
                is ColorDrawable -> Bitmap.createBitmap(
                    COLOR_DRAWABLE_DIMENSION,
                    COLOR_DRAWABLE_DIMENSION,
                    BITMAP_CONFIG
                )
                else -> Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    BITMAP_CONFIG
                )
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun initializeBitmap() {
        bitmap = getBitmapFromDrawable(drawable)
        bitmapCanvas = if (bitmap != null && bitmap!!.isMutable) Canvas(bitmap!!) else null
        if (!initialized) return
        if (bitmap != null) {
            updateShaderMatrix(bitmap)
        } else {
            bitmapPaint.shader = null
        }
    }

    private fun initializePlaceholderBitmap() {
        bitmapPlaceholder = getBitmapFromDrawable(placeholder)
        bitmapCanvas =
            if (bitmapPlaceholder != null && bitmapPlaceholder!!.isMutable) Canvas(bitmapPlaceholder!!) else null
        if (!initialized) return
        if (bitmapPlaceholder != null) {
            updateShaderMatrix(bitmapPlaceholder)
        } else {
            placeholderBitmapPaint.shader = null
        }
    }

    private fun updateDimensions() {
        borderRect.set(calculateBounds())
        borderRadius = min(
            (borderRect.height() - borderWidth) / 2.0f,
            (borderRect.width() - borderWidth) / 2.0f
        )
        drawableRect.set(borderRect)
        if (!borderOverlay && borderWidth > 0) {
            drawableRect.inset(borderWidth - 1.0f, borderWidth - 1.0f)
        }
        drawableRadius = min(drawableRect.height() / 2.0f, drawableRect.width() / 2.0f)
        updateShaderMatrix(bitmap ?: bitmapPlaceholder)
    }

    private fun calculateBounds(): RectF {
        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingTop - paddingBottom
        val sideLength = min(availableWidth, availableHeight)
        val left = paddingLeft + (availableWidth - sideLength) / 2f
        val top = paddingTop + (availableHeight - sideLength) / 2f
        return RectF(left, top, left + sideLength, top + sideLength)
    }

    private fun updateShaderMatrix(bitmap: Bitmap?) {
        if (bitmap == null) return
        val scale: Float
        var dx = 0f
        var dy = 0f
        shaderMatrix.set(null)
        val bitmapHeight = bitmap.height
        val bitmapWidth = bitmap.width
        if (bitmapWidth * drawableRect.height() > drawableRect.width() * bitmapHeight) {
            scale = drawableRect.height() / bitmapHeight.toFloat()
            dx = (drawableRect.width() - bitmapWidth * scale) * 0.5f
        } else {
            scale = drawableRect.width() / bitmapWidth.toFloat()
            dy = (drawableRect.height() - bitmapHeight * scale) * 0.5f
        }
        shaderMatrix.setScale(scale, scale)
        shaderMatrix.postTranslate(
            (dx + 0.5f).toInt() + drawableRect.left,
            (dy + 0.5f).toInt() + drawableRect.top
        )
        rebuildShader = true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (disableCircularTransformation) {
            super.onTouchEvent(event)
        } else inTouchableArea(event.x, event.y) && super.onTouchEvent(event)
    }

    private fun inTouchableArea(x: Float, y: Float): Boolean {
        return if (borderRect.isEmpty) {
            true
        } else {
            (x - borderRect.centerX().toDouble()).pow(2.0) + (y - borderRect.centerY()
                .toDouble()).pow(2.0) <= borderRadius.toDouble().pow(2.0)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private inner class OutlineProvider : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            if (disableCircularTransformation) {
                BACKGROUND.getOutline(view, outline)
            } else {
                val bounds = Rect()
                borderRect.roundOut(bounds)
                outline.setRoundRect(bounds, bounds.width() / 2.0f)
            }
        }
    }

    companion object {
        private const val TAG = "FTLPlaceholderImageView"

        private val SCALE_TYPE = ScaleType.CENTER_CROP

        private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888

        private const val COLOR_DRAWABLE_DIMENSION = 2

        private const val DEFAULT_BORDER_WIDTH = 0
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_IMAGE_ALPHA = 255
        private const val DEFAULT_BORDER_OVERLAY = false
    }
}

data class FTLPlaceholderImageViewTheme(
    @DrawableRes var placeholder: Int
) : ViewTheme()
