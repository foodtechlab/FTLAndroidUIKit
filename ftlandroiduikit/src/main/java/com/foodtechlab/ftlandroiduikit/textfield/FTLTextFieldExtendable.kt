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
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType


class FTLTextFieldExtendable @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private var tvTextSlot: TextView
    private var ivImageSlot: ImageView
    var collapseLines: Int = 3
    var ellipsizedText: String = ""
    var fullText: String = ""
        set(value) {
            field = value
            tvTextSlot.text = fullText

        }
    var isExpandText = false

    var imageType: ImageType = ImageType.CASH
        set(value) {
            field = value
            ivImageSlot.setImageResource(field.imgRes)
        }

    init {
        inflate(context, R.layout.layout_ftl_text_field_extendable, this)
        tvTextSlot = findViewById(R.id.tv_text_slot)
        ivImageSlot = findViewById(R.id.iv_image_slot)

        context.withStyledAttributes(attrs, R.styleable.FTLTextFieldExtendable) {
            isClickable = true
            imageType = ImageType.values()[getInt(R.styleable.FTLTextFieldExtendable_imageType, 6)]
            ivImageSlot.setImageResource(imageType.imgRes)
            fullText = getString(R.styleable.FTLTextFieldExtendable_fullText) ?: ""
            collapseLines = getInt(R.styleable.FTLTextFieldExtendable_collapseLines, 3)
            ellipsizedText = getString(R.styleable.FTLTextFieldExtendable_ellipsizedText) ?: ""
            isExpandText = getBoolean(R.styleable.FTLTextFieldExtendable_isExpand, false)
            tvTextSlot.text = fullText
        }
        setWillNotDraw(false)
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
                    val endIndex = charsCount - ellipsizedText.length
                    val shortText = fullText.subSequence(0, endIndex).toString()
                    tvTextSlot.text = SpannableString(shortText.plus(ellipsizedText)).apply {
                        setSpan(
                            ForegroundColorSpan(
                                ContextCompat.getColor(
                                    context,
                                    R.color.OnBackgroundSecondary
                                )
                            ),
                            endIndex,
                            endIndex + ellipsizedText.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
            }
        }
    }

    override fun performClick(): Boolean {
        setExpand(!isExpandText)
        return super.performClick()
    }

    override fun onDraw(canvas: Canvas) {
        setExpand(isExpandText)
    }
}