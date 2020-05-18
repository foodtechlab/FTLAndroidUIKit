package com.foodtechlab.ftlandroiduikit.textfields

import android.animation.ValueAnimator
import android.content.Context
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isInvisible
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.core.view.updateMargins
import androidx.core.widget.addTextChangedListener
import com.foodtechlab.ftlandroiduikit.R
import com.google.android.material.animation.ArgbEvaluatorCompat

/**
 * Created by Umalt on 12.05.2020
 */
class FTLEditTextDefault @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val hintXTranslation by lazy {
        -((tvHint.width - (HINT_SHRINK_SCALE * tvHint.width)) * HALF)
    }

    private val hintYTranslation by lazy { -((height - etInput.height) * HALF) }

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

    private var isErrorEnabled = false

    var isActiveStateEnabled: Boolean = true
        set(value) {
            field = value
            updateControls()
        }

    var hint: CharSequence
        get() = tvHint.text
        set(value) {
            tvHint.text = value
        }

    private var digits: CharSequence? = null
        set(value) {
            value?.let { etInput.keyListener = DigitsKeyListener.getInstance(it.toString()) }
            field = value
        }

    var onEditorActionListener: TextView.OnEditorActionListener? = null
        set(value) {
            etInput.setOnEditorActionListener(value)
        }

    private val vUnderline: View
    private val tvHint: TextView
    private val etInput: EditText

    init {
        inflate(context, R.layout.layout_ftl_edit_text_default, this)

        vUnderline = findViewById(R.id.v_underline)
        tvHint = findViewById(R.id.tv_hint)

        etInput = findViewById(R.id.et_input)
        etInput.apply {
            setOnFocusChangeListener { _, _ ->
                updateControls()
            }
            addTextChangedListener {
                setErrorEnabled(false)
            }
        }

        orientation = VERTICAL

        background = ContextCompat.getDrawable(context, R.drawable.shape_ftl_edit_text_default)

        context.withStyledAttributes(attrs, R.styleable.FTLEditTextDefault) {
            hint = getText(R.styleable.FTLEditTextDefault_hint) ?: ""

            inputType = getInt(R.styleable.FTLEditTextDefault_inputType, EditorInfo.TYPE_NULL)

            imeOptions = getInt(R.styleable.FTLEditTextDefault_imeOptions, EditorInfo.IME_NULL)

            digits = getText(R.styleable.FTLEditTextDefault_digits)

            isActiveStateEnabled = getBoolean(
                R.styleable.FTLEditTextDefault_isActiveStateEnabled,
                true
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (tvHint.marginTop == 0) {
            val margin = (measuredHeight - tvHint.measuredHeight) / 2
            tvHint.setVerticalMargins(margin)
            etInput.setVerticalMargins(margin)
        }
    }

    private fun View.setVerticalMargins(margin: Int) {
        val lParams = layoutParams as ConstraintLayout.LayoutParams
        lParams.updateMargins(
            marginStart,
            margin,
            marginStart,
            margin
        )
        layoutParams = lParams
    }

    private fun updateControls() {
        updateHint()
        updateUnderline()
    }

    private fun updateHint() {
        scaleHint()
        tintHint()
    }

    private fun updateUnderline() {
        vUnderline.apply {
            isInvisible = !isHintOnTop && !isErrorEnabled
            setBackgroundColor(controlsColor)
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    private fun scaleHint() {
        val scale = if (!isHintOnTop && !isErrorEnabled) 1f else HINT_SHRINK_SCALE
        val translationX = if (!isHintOnTop && !isErrorEnabled) 0f else hintXTranslation
        val translationY = if (!isHintOnTop && !isErrorEnabled) 0f else hintYTranslation

        tvHint.animate().apply {
            scaleX(scale)
            scaleY(scale)
            translationX(translationX)
            translationY(translationY)
            duration = HINT_ANIMATION_DURATION
            start()
        }
    }

    private fun tintHint() {
        ValueAnimator.ofObject(ArgbEvaluatorCompat(), tvHint.currentTextColor, controlsColor)
            .apply {
                duration = HINT_ANIMATION_DURATION
                addUpdateListener { animation ->
                    tvHint.setTextColor(animation.animatedValue as Int)
                }
                start()
            }
    }

    fun setErrorEnabled(isEnabled: Boolean) {
        isErrorEnabled = isEnabled
        updateControls()
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        etInput.addTextChangedListener(watcher)
    }

    fun removeTextChangedListener(watcher: TextWatcher) {
        etInput.removeTextChangedListener(watcher)
    }

    companion object {
        private const val HINT_ANIMATION_DURATION = 200L
        private const val HINT_SHRINK_SCALE = .8f
        private const val HALF = .5f
    }
}