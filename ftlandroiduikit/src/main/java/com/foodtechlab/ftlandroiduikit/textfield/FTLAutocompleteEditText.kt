package com.foodtechlab.ftlandroiduikit.textfield

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.*
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.*

class FTLAutocompleteEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle), ThemeManager.ThemeChangedListener {
    var isErrorEnabled = false
        set(value) {
            field = value
            updateControls()
        }

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

    var minCharsCount: Int = 0
        set(value) {
            field = value
            etInput.threshold = field
        }

    var maxDropDownHeightForFolding: Int = context.dpToPxInt(MAX_DROP_DOWN_HEIGHT)

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

    lateinit var etInput: AutoCompleteTextView
        private set

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
    private val ivClearText: ImageView

    init {
        inflate(context, R.layout.layout_ftl_autocomplete_edit_text, this)

        vUnderline = findViewById(R.id.v_underline)
        ivClearText = findViewById(R.id.iv_clear)

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
                    ivClearText.isVisible = s?.isNotEmpty() == true
                }
            })
            updateMargins()
            dropDownVerticalOffset = context.dpToPx(4f).toInt()
            setOnClickListener {
                clickListener?.onClick(this@FTLAutocompleteEditText)
            }
        }

        minimumHeight = context.dpToPx(MIN_HEIGHT).toInt()

        background = ContextCompat.getDrawable(context, R.drawable.shape_ftl_edit_text_default)
        onThemeChanged(ThemeManager.theme)

        super.setOnClickListener {
            etInput.openKeyboard()
            etInput.requestFocus()

            clickListener?.onClick(it)
        }

        ivClearText.setOnClickListener {
            etInput.text.clear()
        }

        context.withStyledAttributes(attrs, R.styleable.FTLAutocompleteEditText) {
            hint = getString(R.styleable.FTLAutocompleteEditText_hint) ?: ""

            maxLength = getInt(R.styleable.FTLAutocompleteEditText_maxLength, -1)

            maxLines = getInt(R.styleable.FTLAutocompleteEditText_maxLineCount, Int.MAX_VALUE)

            text = getString(R.styleable.FTLAutocompleteEditText_text) ?: ""

            inputType =
                getInt(R.styleable.FTLAutocompleteEditText_inputType, EditorInfo.TYPE_CLASS_TEXT)

            imeOptions = getInt(R.styleable.FTLAutocompleteEditText_imeOptions, EditorInfo.IME_NULL)

            filters = getString(R.styleable.FTLAutocompleteEditText_filters)
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
            setTextColor(ContextCompat.getColor(context, theme.ftlCompleteEditTextTheme.textColor))
            setHintTextColor(
                ContextCompat.getColor(
                    context,
                    theme.ftlCompleteEditTextTheme.hintColor
                )
            )
            setDropDownBackgroundResource(theme.ftlCompleteEditTextTheme.dropDownBackgroundDrawable)
        }

        DrawableCompat.setTint(
            ivClearText.drawable,
            ContextCompat.getColor(context, theme.ftlCompleteEditTextTheme.clearIconTintColor)
        )
        background.changeColor(
            ContextCompat.getColor(
                context,
                theme.ftlCompleteEditTextTheme.bgColor
            )
        )
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        etInput.addTextChangedListener(watcher)
    }

    fun removeTextChangedListener(watcher: TextWatcher) {
        etInput.removeTextChangedListener(watcher)
    }

    fun <T> setHintsAdapter(adapter: T) where T : ListAdapter?, T : Filterable? {
        etInput.setAdapter(adapter)
    }

    fun showHints() {
        etInput.showDropDown()
    }

    fun hideHints() {
        etInput.dismissDropDown()
    }

    fun disableSelectionActionPanel() {
        with(etInput) {
            isLongClickable = false
            setTextIsSelectable(false)
            customSelectionActionModeCallback = object : ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode?, menu: Menu): Boolean {
                    return false
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu): Boolean {
                    return false
                }

                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem): Boolean {
                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode?) {}
            }
        }
    }

    /**
     * Метод изменяет высоту popup до значения maxDropDownHeightForFolding в случае если размер
     * фильтрованного списка меньше чем значение maxItemCountForFolding, в противном случае высота
     * popup будет WRAP_CONTENT, что не всегда удачное решение на android 5.0
     * @param hintsList Список подсказок (нефильтрованный)
     * @param charSequence Введеное значение в поле ввода
     * @param maxItemCountForFolding Максимальное количество элементов для регулирования высоты
     */
    fun foldingDropDownDialog(
        hintsList: ArrayList<String>,
        charSequence: CharSequence?,
        maxItemCountForFolding: Int
    ) {
        val queryString = charSequence?.toString()?.lowercase()
        val filterResults = arrayListOf<String>()
        filterResults.addAll(when {
            queryString.isNullOrEmpty() -> hintsList
            else -> hintsList.filter {
                it.lowercase().contains(queryString)
            }
        })

        etInput.dropDownHeight = if (filterResults.size < maxItemCountForFolding) {
            ViewGroup.LayoutParams.WRAP_CONTENT
        } else {
            maxDropDownHeightForFolding
        }
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
        private const val MAX_DROP_DOWN_HEIGHT = 88f
    }
}