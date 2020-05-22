package com.foodtechlab.ftlandroiduikit.textfield

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isInvisible
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.core.view.updateMargins
import androidx.core.widget.addTextChangedListener
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.dpToPx
import com.foodtechlab.ftlandroiduikit.util.openKeyboard
import com.foodtechlab.ftlandroiduikit.util.spToPx

/**
 * Created by Umalt on 12.05.2020
 */
class FTLEditTextDefault @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle) {

    var textGravity: Int = Gravity.START
        set(value) {
            field = value
            etInput.gravity = value
        }

    var marginHorizontal: Float = context.dpToPx(DEFAULT_MARGIN_HORIZONTAL)
        set(value) {
            field = value
            etInput.updateMargins()
        }

    private var scale = SCALE_MAX

    private val isHintOnTop: Boolean
        get() = etInput.hasFocus() || !etInput.text.isNullOrEmpty()

    private val controlsColor: Int
        get() = ContextCompat.getColor(
            context, when {
                isErrorEnabled -> R.color.PrimaryDangerEnabled
                !etInput.hasFocus() && !etInput.text.isNullOrEmpty() && !isActiveStateEnabled -> R.color.PrimaryInfoEnabled
                !etInput.hasFocus() && !etInput.text.isNullOrEmpty() -> R.color.OnBackgroundSecondary
                isHintOnTop -> R.color.PrimaryInfoEnabled
                else -> R.color.OnBackgroundSecondary
            }
        )

    var inputType: Int
        get() = etInput.inputType
        set(value) {
            etInput.inputType = value
        }

    var imeOptions: Int
        get() = etInput.imeOptions
        set(value) {
            etInput.imeOptions = value
        }

    var maxLength: Int
        get() = (etInput.filters[0] as? InputFilter.LengthFilter)?.max ?: -1
        set(value) {
            etInput.filters = if (value >= 0) {
                arrayOf(InputFilter.LengthFilter(value))
            } else {
                arrayOfNulls(0)
            }
        }

    var isErrorEnabled = false
        set(value) {
            field = value
            updateControls()
        }

    var isActiveStateEnabled: Boolean = true
        set(value) {
            field = value
            updateControls()
        }

    var hint: String = ""
        set(value) {
            field = value
            invalidate()
        }

    private val initialHintTextSize by lazy { context.spToPx(INITIAL_HINT_TEXT_SIZE) }

    private val initialHeight by lazy { height }

    private val hintPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = controlsColor
            textSize = initialHintTextSize
            if (!isInEditMode) {
                typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
            }
        }
    }

    private var digits: CharSequence? = null
        set(value) {
            value?.let { etInput.keyListener = DigitsKeyListener.getInstance(it.toString()) }
            field = value
        }

    var keyListener: OnKeyListener? = null
        set(value) {
            etInput.setOnKeyListener(value)
            field = value
        }

    var editorActionListener: TextView.OnEditorActionListener? = null
        set(value) {
            etInput.setOnEditorActionListener(value)
            field = value
        }

    private val vUnderline: View
    val etInput: EditText

    init {
        inflate(context, R.layout.layout_ftl_edit_text_default, this)

        vUnderline = findViewById(R.id.v_underline)

        etInput = findViewById(R.id.et_input)
        etInput.apply {
            setOnFocusChangeListener { _, _ ->
                openKeyboard()
                updateControls()
            }
            addTextChangedListener {
                isErrorEnabled = false
            }
            updateMargins()
        }

        minimumHeight = context.dpToPx(MIN_HEIGHT).toInt()

        background = ContextCompat.getDrawable(context, R.drawable.shape_ftl_edit_text_default)

        context.withStyledAttributes(attrs, R.styleable.FTLEditTextDefault) {
            hint = getString(R.styleable.FTLEditTextDefault_hint) ?: ""

            inputType = getInt(
                R.styleable.FTLEditTextDefault_inputType,
                EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE
            )

            imeOptions = getInt(R.styleable.FTLEditTextDefault_imeOptions, EditorInfo.IME_NULL)

            digits = getText(R.styleable.FTLEditTextDefault_digits)

            maxLength = getInt(R.styleable.FTLEditTextDefault_maxLength, -1)

            isActiveStateEnabled = getBoolean(
                R.styleable.FTLEditTextDefault_isActiveStateEnabled,
                true
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawHint()

        etInput.y = if (hint.isEmpty()) {
            (height / 2 - etInput.height / 2).toFloat()
        } else {
            (height / 2).toFloat()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        etInput.minimumWidth = width
        vUnderline.minimumWidth = width
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            return true
        } else if (ev.action == MotionEvent.ACTION_UP) {
            etInput.requestFocus()
            return true
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun Canvas.drawHint() {
        val hintHeight = hintPaint.fontMetrics.descent - hintPaint.fontMetrics.ascent
        val hintY = (initialHeight / 2 + hintHeight / 2 - hintPaint.fontMetrics.bottom) * scale
        hintPaint.textSize = initialHintTextSize * scale

        hintPaint.color = controlsColor

        drawText(hint, marginHorizontal, hintY, hintPaint)
    }

    private fun View.updateMargins() {
        val lParams = layoutParams as LayoutParams
        lParams.updateMargins(
            marginHorizontal.toInt(),
            marginTop,
            marginHorizontal.toInt(),
            marginBottom
        )
        layoutParams = lParams
    }

    private fun updateControls() {
        updateHint()
        updateUnderline()
    }

    private fun updateUnderline() {
        vUnderline.apply {
            isInvisible = !isHintOnTop && !isErrorEnabled
            setBackgroundColor(controlsColor)
        }
    }

    private fun updateHint() {
        if (hintPaint.color != controlsColor) {
            invalidate()
        }

        val startVal = if (!isHintOnTop && !isErrorEnabled) SCALE_MIN else SCALE_MAX
        val endVal = if (!isHintOnTop && !isErrorEnabled) SCALE_MAX else SCALE_MIN

        if (scale != endVal) {
            ValueAnimator.ofFloat(startVal, endVal).apply {
                duration = ANIMATION_DURATION

                addUpdateListener { animation ->
                    scale = animation.animatedValue as Float
                    invalidate()
                }
                start()
            }
        }
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        etInput.addTextChangedListener(watcher)
    }

    fun removeTextChangedListener(watcher: TextWatcher) {
        etInput.removeTextChangedListener(watcher)
    }

    fun updateFieldState(blocked: Boolean) {
        etInput.isFocusable = !blocked
        etInput.isFocusableInTouchMode = !blocked
        etInput.isCursorVisible = !blocked

        if (blocked) {
            etInput.clearFocus()
        } else {
            etInput.requestFocus()
        }
    }

    companion object {
        private const val ANIMATION_DURATION = 200L

        private const val SCALE_MAX = 1f
        private const val SCALE_MIN = .7f
        private const val DEFAULT_MARGIN_HORIZONTAL = 16f
        private const val INITIAL_HINT_TEXT_SIZE = 16f
        private const val MIN_HEIGHT = 56f
    }
}