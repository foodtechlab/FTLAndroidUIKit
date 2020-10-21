package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.graphics.Canvas
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.util.ThemeManager
import com.foodtechlab.ftlandroiduikit.util.changeColor
import com.foodtechlab.ftlandroiduikit.util.dpToPx


class FTLExtendableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), ThemeManager.ThemeChangedListener {

    var isExpandText = false

    var collapseLines: Int = 3

    var ellipsizedText: String = ""

    var fullText: String = ""
        set(value) {
            field = value
            tvTextSlot.text = fullText

        }
    var imageType: ImageType = ImageType.CASH
        set(value) {
            field = value
            ivImageSlot.setImageResource(field.imgRes)
        }

    @ColorInt
    var imageBackgroundColor = ContextCompat.getColor(context, R.color.IconBackgroundBlueLight)
        set(value) {
            field = value
            ivImageSlot.background?.changeColor(value)
        }

    @ColorInt
    var imageColor = ContextCompat.getColor(context, R.color.IconPrimaryLight)
        set(value) {
            field = value
            ivImageSlot.setColorFilter(field)
        }

    private var tvTextSlot: TextView
    private var ivImageSlot: ImageView

    init {
        inflate(context, R.layout.layout_ftl_extendable_text_view, this)

        orientation = HORIZONTAL

        tvTextSlot = findViewById(R.id.tv_text_slot)
        ivImageSlot = findViewById(R.id.iv_image_slot)

        context.withStyledAttributes(attrs, R.styleable.FTLExtendableTextView) {
            isClickable = true
            imageType = ImageType.values()[getInt(R.styleable.FTLExtendableTextView_imageType, 6)]
            fullText = getString(R.styleable.FTLExtendableTextView_fullText) ?: ""
            collapseLines = getInt(R.styleable.FTLExtendableTextView_collapseLines, 3)
            ellipsizedText = getString(R.styleable.FTLExtendableTextView_ellipsizedText) ?: ""
            isExpandText = getBoolean(R.styleable.FTLExtendableTextView_isExpand, false)
            imageBackgroundColor = getColor(
                R.styleable.FTLExtendableTextView_imageBackgroundColor,
                imageBackgroundColor
            )
            imageColor = getColor(
                R.styleable.FTLExtendableTextView_imageColor,
                imageColor
            )
        }
        onThemeChanged(ThemeManager.theme)
        setWillNotDraw(false)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ThemeManager.addListener(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
    }

    override fun onThemeChanged(theme: ThemeManager.Theme) {
        tvTextSlot.setTextColor(
            ContextCompat.getColor(context, theme.ftlExtendableTextViewTheme.fullTextColor)
        )
        if (!isExpandText && tvTextSlot.lineCount >= collapseLines) {
            val charsCount = tvTextSlot.layout.getLineStart(collapseLines)
            setEllipsizedTextColor(charsCount)
        }
    }

    override fun performClick(): Boolean {
        setExpand(!isExpandText)
        return super.performClick()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (tvTextSlot.lineCount == 1) {
            val paddingTop = context.dpToPx(4f).toInt()
            tvTextSlot.setPadding(0, paddingTop, 0, 0)
        }
        setExpand(isExpandText)
    }

    private fun setExpand(expand: Boolean) {
        isExpandText = expand
        if (expand) {
            tvTextSlot.maxLines = Integer.MAX_VALUE
            tvTextSlot.text = fullText
        } else {
            tvTextSlot.maxLines = collapseLines
            if (tvTextSlot.lineCount >= collapseLines) {
                val charsCount = tvTextSlot.layout.getLineStart(collapseLines)
                if (charsCount > 0 && tvTextSlot.text.length > charsCount) {
                    setEllipsizedTextColor(charsCount)
                }
            }
        }
    }

    private fun setEllipsizedTextColor(charsCount: Int) {
        val endIndex = charsCount - ellipsizedText.length
        val shortText = fullText.subSequence(0, endIndex).toString()
        tvTextSlot.text = SpannableString(shortText.plus(ellipsizedText)).apply {
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        context,
                        ThemeManager.theme.ftlExtendableTextViewTheme.ellipsizedTextColor
                    )
                ),
                endIndex,
                endIndex + ellipsizedText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
}