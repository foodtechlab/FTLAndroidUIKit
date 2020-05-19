package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Space
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.TextWatcher
import com.foodtechlab.ftlandroiduikit.util.dpToPx

/**
 * Created by Umalt on 18.05.2020
 */
class CodeEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), View.OnKeyListener {

    val code: String
        get() = StringBuilder(symbolsCount).apply {
            codeSymbols.forEach {
                if (it != -1) {
                    append(it.toString())
                }
            }
        }.toString()

    var symbolsCount: Int = DEFAULT_SYMBOLS_COUNT
        set(value) {
            field = value
            addFields()
        }

    var isErrorEnabled: Boolean = false
        set(value) {
            field = value
            for (i in 0 until symbolsCount) {
                inputFields[i].setErrorEnabled(value)
            }
        }

    private val codeSymbols = arrayListOf<Int>()

    private val llFields: LinearLayout

    private val inputFields = arrayListOf<FTLEditTextDefault>()

    init {
        View.inflate(context, R.layout.layout_code_edit_text, this)

        llFields = findViewById(R.id.ll_fields)

        context.withStyledAttributes(attrs, R.styleable.CodeEditText) {
            symbolsCount = getInt(R.styleable.CodeEditText_symbolsCount, DEFAULT_SYMBOLS_COUNT)
            for (i in 0 until symbolsCount) {
                codeSymbols.add(-1)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val field = codeSymbols.firstOrNull { it == -1 }
            ?.let { inputFields[codeSymbols.indexOf(it)] }
            ?: inputFields.last()

        field.apply {
            updateFieldState(false)
            requestFocus()
        }

        return super.dispatchTouchEvent(ev)
    }

    override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_UP) {
            val field = v as EditText
            if (field.text.isNullOrEmpty()) {
                val curId = v.id
                val prevId = v.id - 1
                codeSymbols[curId] = -1
                inputFields[curId].updateFieldState(curId > 0)
                inputFields.getOrNull(prevId)?.apply {
                    codeSymbols[prevId] = -1
                    etInput.text = null
                    updateFieldState(false)
                    requestFocus()
                }
            }
            return true
        }
        return false
    }

    private fun addFields() {
        inputFields.clear()
        llFields.removeAllViews()

        for (i in 0 until symbolsCount) {
            val field = FTLEditTextDefault(context).apply {
                etInput.id = i
                minimumWidth = context.dpToPx(MIN_FIELD_WIDTH_DP).toInt()
                inputType = EditorInfo.TYPE_CLASS_NUMBER
                isActiveStateEnabled = false
                maxLength = 1
                keyListener = this@CodeEditText
                updateFieldState(true)
                addTextChangedListener(GenericTextWatcher(etInput))
            }
            inputFields.add(field)
            llFields.addView(field)

            if (i < symbolsCount - 1) {
                val space = Space(context).apply {
                    minimumWidth = context.dpToPx(MIN_SPACE_WIDTH_DP).toInt()
                }
                llFields.addView(space)
            }
        }
    }

    private fun FTLEditTextDefault.updateFieldState(blocked: Boolean) {
        etInput.isFocusable = !blocked
        etInput.isFocusableInTouchMode = !blocked
        etInput.isCursorVisible = !blocked
    }

    private inner class GenericTextWatcher(private val view: View) : TextWatcher() {

        override fun afterTextChanged(s: Editable?) {
            super.afterTextChanged(s)

            isErrorEnabled = false

            val text = s.toString()

            val curId = view.id
            val nextId = if (curId + 1 < symbolsCount) curId + 1 else -1

            when (text.length) {
                1 -> {
                    codeSymbols[curId] = Integer.parseInt(text)
                    inputFields[curId].updateFieldState(true)
                    inputFields.getOrNull(nextId)?.apply {
                        updateFieldState(false)
                        requestFocus()
                    }
                }
                0 -> {
                    codeSymbols[curId] = -1
                    inputFields[curId].apply {
                        updateFieldState(false)
                        requestFocus()
                    }
                }
            }
        }
    }

    companion object {
        private const val DEFAULT_SYMBOLS_COUNT = 4
        private const val MIN_FIELD_WIDTH_DP = 40F
        private const val MIN_SPACE_WIDTH_DP = 16F
    }
}