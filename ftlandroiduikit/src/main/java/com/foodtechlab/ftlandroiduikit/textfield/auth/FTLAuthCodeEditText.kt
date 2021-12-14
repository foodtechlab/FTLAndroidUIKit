package com.foodtechlab.ftlandroiduikit.textfield.auth

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.ViewTheme
import com.foodtechlab.ftlandroiduikit.util.ViewThemeManager
import com.foodtechlab.ftlandroiduikit.util.spToPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.math.min

/**
 * Created by Umalt on 25.05.2020
 */
class FTLAuthCodeEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr), CoroutineScope {
    var isErrorEnabled = false
        set(value) {
            field = value
            invalidate()
        }

    private val viewThemeManager: ViewThemeManager<FTLAuthCodeEditTextTheme> =
        FTLAuthCodeEditTextThemeManager()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private var ftlAuthCodeEditTextTheme = viewThemeManager.lightTheme
    private var blink = false
    private val displayDensity = resources.displayMetrics.density
    private val gap = DEFAULT_GAP * displayDensity
    private var charWidth = 0f
    private var cursorStartX = paddingLeft.toFloat()
    private var maxLength: Int
        get() = (filters[0] as? InputFilter.LengthFilter)?.max ?: -1
        set(value) {
            filters = if (value >= 0) {
                arrayOf(InputFilter.LengthFilter(value))
            } else {
                arrayOfNulls(0)
            }
        }

    private val bgCorners = floatArrayOf(
        5f * displayDensity, 5f * displayDensity, // top left corner radius in dp
        5f * displayDensity, 5f * displayDensity, // top right corner radius in dp
        0f, 0f,                     // bottom right corner radius in dp
        0f, 0f                      // bottom left corner radius in dp
    )

    private val bgPath = Path()
    private val bgRect = RectF()
    private var charWidths: FloatArray
    private var onClickListener: OnClickListener? = null
    private var textWatcher: TextWatcher? = null
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val cursorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = ContextCompat.getColor(
                context,
                ftlAuthCodeEditTextTheme.textColor
            )
            textSize = context.spToPx(TEXT_SIZE)
            if (!isInEditMode) {
                typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
            }
        }
    }

    private val bgPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color =
                ContextCompat.getColor(context, ftlAuthCodeEditTextTheme.bgColor)
        }
    }

    init {
        setBackgroundResource(0)

        maxLines = 1
        minimumHeight = (MIN_HEIGHT * displayDensity).toInt()
        isCursorVisible = false
        includeFontPadding = false

        val length = attrs?.getAttributeIntValue(
            XML_NAMESPACE_ANDROID,
            "maxLength",
            DEFAULT_MAX_LENGTH
        ) ?: DEFAULT_MAX_LENGTH

        maxLength = min(POSSIBLE_MAX_LENGTH, length)

        charWidths = FloatArray(maxLength)

        val horizontalPadding = paddingStart + paddingEnd
        val charsWidth = maxLength * MIN_CHAR_WIDTH * displayDensity
        val gapsWidth = (maxLength - 1) * gap
        minWidth = (charsWidth + gapsWidth + horizontalPadding).toInt()

        // Disable copy paste
        super.setCustomSelectionActionModeCallback(object : ActionMode.Callback {
            override fun onActionItemClicked(
                mode: ActionMode?,
                item: MenuItem?
            ): Boolean {
                return false
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {}
        })

        // Set cursor at the end of text
        super.setOnClickListener { v ->
            setSelection(text?.length ?: 0)
            onClickListener?.onClick(v)
        }

        super.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                isErrorEnabled = false
                textWatcher?.afterTextChanged(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                textWatcher?.beforeTextChanged(s, start, count, after)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textWatcher?.onTextChanged(s, start, before, count)
            }
        })

        postDelayed(object : Runnable {
            override fun run() {
                blink = !blink
                cursorPaint.apply {
                    color = ContextCompat.getColor(
                        context,
                        if (blink) ftlAuthCodeEditTextTheme.activeControlColor else ftlAuthCodeEditTextTheme.bgColor
                    )
                    strokeWidth = if (blink) 2f * displayDensity else 0f
                }
                invalidate()
                postDelayed(this, 500)
            }
        }, 300)
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

    override fun onDraw(canvas: Canvas) {
        val availableWidth = width - paddingStart - paddingEnd

        charWidth = (availableWidth - gap * (maxLength - 1)) / maxLength

        var startX = paddingStart.toFloat()

        textPaint.getTextWidths(text, 0, text?.length ?: 0, charWidths)

        val cursorPosition = text?.length ?: 0

        for (i in 0 until maxLength) {
            canvas.drawBackground(startX)
            canvas.drawText(startX, i)

            if (i == cursorPosition) cursorStartX = startX

            val isEmptyLastChar = text?.getOrNull(maxLength - 1)?.toString().isNullOrEmpty()

            if (startX == cursorStartX && hasFocus() && isEmptyLastChar) {
                canvas.drawCursor()
            } else {
                canvas.drawUnderline(startX, text?.getOrNull(i)?.toString())
            }

            startX += charWidth + gap
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        onClickListener = l
    }

    override fun setCustomSelectionActionModeCallback(actionModeCallback: ActionMode.Callback?) {
        throw RuntimeException("setCustomSelectionActionModeCallback() not supported.")
    }

    fun onThemeChanged(theme: FTLAuthCodeEditTextTheme) {
        ftlAuthCodeEditTextTheme = theme
        bgPaint.color =
            ContextCompat.getColor(context, theme.bgColor)
        textPaint.color =
            ContextCompat.getColor(context, theme.textColor)
        invalidate()
    }

    fun setTextWatcher(watcher: TextWatcher?) {
        textWatcher = watcher
    }

    private fun Canvas.drawText(startX: Float, position: Int) {
        val bottomWithPadding = (height - paddingBottom).toFloat()
        val textHeight = textPaint.fontMetrics.descent - textPaint.fontMetrics.ascent

        if (text?.length ?: 0 > position) {
            val posX = startX + charWidth * HALF - charWidths[0] * HALF
            val posY = bottomWithPadding - textHeight * HALF
            drawText(text.toString(), position, position + 1, posX, posY, textPaint)
        }
    }

    private fun Canvas.drawBackground(startX: Float) {
        bgPath.reset()
        bgRect.set(startX, 0f, startX + charWidth, height.toFloat())
        bgPath.addRoundRect(bgRect, bgCorners, Path.Direction.CW)
        drawPath(bgPath, bgPaint)
    }

    private fun Canvas.drawUnderline(startX: Float, char: String?) {
        when {
            isErrorEnabled -> linePaint.apply {
                color = ContextCompat.getColor(
                    context,
                    ftlAuthCodeEditTextTheme.errorControlColor
                )
                strokeWidth = 2f * displayDensity
            }
            char.isNullOrEmpty() -> linePaint.apply {
                color = ContextCompat.getColor(
                    context,
                    ftlAuthCodeEditTextTheme.bgColor
                )
                strokeWidth = 0f
            }
            else -> linePaint.apply {
                color = ContextCompat.getColor(
                    context,
                    ftlAuthCodeEditTextTheme.activeControlColor
                )
                strokeWidth = 2f * displayDensity
            }
        }

        drawLine(
            startX,
            height.toFloat(),
            startX + charWidth,
            height.toFloat(),
            linePaint
        )
    }

    private fun Canvas.drawCursor() {
        drawLine(
            cursorStartX,
            height.toFloat(),
            cursorStartX + charWidth,
            height.toFloat(),
            cursorPaint
        )
    }

    companion object {
        private const val XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android"

        private const val DEFAULT_GAP = 16
        private const val DEFAULT_MAX_LENGTH = 4
        private const val POSSIBLE_MAX_LENGTH = 5

        private const val HALF = .5f
        private const val TEXT_SIZE = 16f
        private const val MIN_HEIGHT = 56f
        private const val MIN_CHAR_WIDTH = 40f
    }
}

data class FTLAuthCodeEditTextTheme(
    @ColorRes val textColor: Int,
    @ColorRes val hintColor: Int,
    @ColorRes val bgColor: Int,
    @ColorRes val defaultControlColor: Int,
    @ColorRes val errorControlColor: Int,
    @ColorRes val activeControlColor: Int
) : ViewTheme()
