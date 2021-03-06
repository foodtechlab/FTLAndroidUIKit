package com.foodtechlab.ftlandroiduikit.textfield

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isInvisible
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.core.view.updateMargins
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.*

/**
 * Created by Umalt on 12.05.2020
 */
class FTLEditTextDefault @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle), ThemeManager.ThemeChangedListener {

    var textGravity: Int = Gravity.START
        set(value) {
            field = value
            etInput.gravity = value
        }

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

    var isErrorEnabled = false
        set(value) {
            field = value
            updateControls()
        }

    var hint: String = ""
        set(value) {
            field = value
            invalidate()
        }

    var text: String
        get() = etInput.text?.toString() ?: ""
        set(value) {
            etInput.setText(value)
        }

    var editorActionListener: TextView.OnEditorActionListener? = null
        set(value) {
            etInput.setOnEditorActionListener(value)
            field = value
        }

    lateinit var etInput: AppCompatEditText

    private var scale = SCALE_MAX

    private val isHintOnTop: Boolean
        get() = etInput.hasFocus() || !etInput.text.isNullOrEmpty()

    private val controlsColor: Int
        get() = ContextCompat.getColor(
            context, when {
                isErrorEnabled -> ThemeManager.theme.ftlEditTextDefaultTheme.errorControlColor
                !etInput.hasFocus() && !etInput.text.isNullOrEmpty() -> ThemeManager.theme.ftlEditTextDefaultTheme.defaultControlColor
                isHintOnTop -> ThemeManager.theme.ftlEditTextDefaultTheme.activeControlColor
                else -> ThemeManager.theme.ftlEditTextDefaultTheme.defaultControlColor
            }
        )

    private var maxLength: Int
        get() = (etInput.filters[0] as? InputFilter.LengthFilter)?.max ?: -1
        set(value) {
            etInput.filters = if (value >= 0) {
                arrayOf(InputFilter.LengthFilter(value))
            } else {
                arrayOfNulls(0)
            }
        }

    private var maxLines: Int
        get() = etInput.maxLines
        set(value) {
            etInput.maxLines = value
        }

    private val initialHintTextSize by lazy { context.spToPx(INITIAL_HINT_TEXT_SIZE) }

    private val initialHeight by lazy {
        val lineHeight = etInput.height / etInput.lineCount
        height - lineHeight * (etInput.lineCount - 1)
    }

    private val hintPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = controlsColor
            textSize = initialHintTextSize
            if (!isInEditMode) {
                typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
            }
        }
    }

    private var filters: String? = null
        set(value) {
            value?.let { setFilterDigits(it) }
            field = value
        }

    private var textWatcher: TextWatcher? = null

    private var focusChangeListener: OnFocusChangeListener? = null

    private var clickListener: OnClickListener? = null

    private val vUnderline: View

    init {
        inflate(context, R.layout.layout_ftl_edit_text_default, this)

        vUnderline = findViewById(R.id.v_underline)

        etInput = findViewById(R.id.et_input)
        etInput.apply {
            setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) openKeyboard()
                updateControls()
                focusChangeListener?.onFocusChange(v, hasFocus)
            }
            addTextChangedListener(object :
                com.foodtechlab.ftlandroiduikit.textfield.helper.TextWatcher() {
                override fun afterTextChanged(s: Editable?) {
                    isErrorEnabled = false
                }
            })
            updateMargins()
        }

        minimumHeight = context.dpToPx(MIN_HEIGHT).toInt()

        background = ContextCompat.getDrawable(context, R.drawable.shape_ftl_edit_text_default)
        onThemeChanged(ThemeManager.theme)

        super.setOnClickListener {
            etInput.openKeyboard()
            etInput.requestFocus()

            clickListener?.onClick(it)
        }

        context.withStyledAttributes(attrs, R.styleable.FTLEditTextDefault) {
            hint = getString(R.styleable.FTLEditTextDefault_hint) ?: ""

            maxLength = getInt(R.styleable.FTLEditTextDefault_maxLength, -1)

            maxLines = getInt(R.styleable.FTLEditTextDefault_maxLineCount, Int.MAX_VALUE)

            text = getString(R.styleable.FTLEditTextDefault_text) ?: ""

            inputType = getInt(R.styleable.FTLEditTextDefault_inputType, EditorInfo.TYPE_CLASS_TEXT)

            imeOptions = getInt(R.styleable.FTLEditTextDefault_imeOptions, EditorInfo.IME_NULL)

            filters = getString(R.styleable.FTLEditTextDefault_filters)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawHint()

        etInput.y = if (hint.isEmpty()) {
            (initialHeight / 2 - etInput.height / 2).toFloat()
        } else {
            (initialHeight / 2).toFloat()
        }

        vUnderline.y = (height - vUnderline.height).toFloat()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        clickListener = l
    }

    override fun setOnFocusChangeListener(l: OnFocusChangeListener?) {
        focusChangeListener = l
    }

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        with(etInput) {
            setTextColor(ContextCompat.getColor(context, theme.ftlEditTextDefaultTheme.textColor))
            setHintTextColor(
                ContextCompat.getColor(
                    context,
                    theme.ftlEditTextDefaultTheme.hintColor
                )
            )
        }
        background.changeColor(
            ContextCompat.getColor(
                context,
                theme.ftlEditTextDefaultTheme.bgColor
            )
        )
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        etInput.addTextChangedListener(watcher)
    }

    fun removeTextChangedListener(watcher: TextWatcher) {
        etInput.removeTextChangedListener(watcher)
    }

    private fun Canvas.drawHint() {
        val hintHeight = hintPaint.fontMetrics.descent - hintPaint.fontMetrics.ascent
        val hintY = (initialHeight / 2 + hintHeight / 2 - hintPaint.fontMetrics.bottom) * scale
        hintPaint.textSize = initialHintTextSize * scale

        hintPaint.color = controlsColor

        drawText(hint, context.dpToPx(MARGIN_HORIZONTAL), hintY, hintPaint)
    }

    private fun View.updateMargins() {
        val lParams = layoutParams as LayoutParams
        val marginHorizontal = context.dpToPx(MARGIN_HORIZONTAL).toInt()
        lParams.updateMargins(
            marginHorizontal,
            marginTop,
            marginHorizontal,
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

    private fun setFilterDigits(digits: String) {
        val filters = arrayOfNulls<InputFilter>(1)
        filters[0] =
            InputFilter { source, start, end, _, _, _ ->
                if (end > start) {
                    for (index in start until end) {
                        if (!digits.contains(source[index].toString())) {
                            return@InputFilter ""
                        }
                    }
                }
                null
            }

        etInput.filters = filters
    }

    companion object {
        private const val ANIMATION_DURATION = 200L
        private const val SCALE_MAX = 1f
        private const val SCALE_MIN = .7f
        private const val MARGIN_HORIZONTAL = 16f
        private const val INITIAL_HINT_TEXT_SIZE = 16f
        private const val MIN_HEIGHT = 56f
    }
}