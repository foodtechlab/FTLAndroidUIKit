package com.foodtechlab.ftlandroiduikit.textfield

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginStart
import androidx.core.view.updateMargins
import com.foodtechlab.ftlandroiduikit.R
import com.foodtechlab.ftlandroiduikit.textfield.helper.ImageType
import com.foodtechlab.ftlandroiduikit.util.dpToPx


class FTLExtendableTextView @JvmOverloads constructor(
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

    @ColorRes
    var backgroundColorRes = -1
        set(value) {
            field = value
            ivImageSlot.backgroundTintList = ColorStateList.valueOf(field)
        }

    @ColorRes
    var imageColorRes = -1
        set(value) {
            field = value
            ivImageSlot.setColorFilter(field)
        }

    init {
        inflate(context, R.layout.layout_ftl_extendable_text_view, this)
        tvTextSlot = findViewById(R.id.tv_text_slot)
        ivImageSlot = findViewById(R.id.iv_image_slot)

        context.withStyledAttributes(attrs, R.styleable.FTLExtendableTextView) {
            isClickable = true
            imageType = ImageType.values()[getInt(R.styleable.FTLExtendableTextView_imageType, 6)]
            fullText = getString(R.styleable.FTLExtendableTextView_fullText) ?: ""
            collapseLines = getInt(R.styleable.FTLExtendableTextView_collapseLines, 3)
            ellipsizedText = getString(R.styleable.FTLExtendableTextView_ellipsizedText) ?: ""
            isExpandText = getBoolean(R.styleable.FTLExtendableTextView_isExpand, false)
            backgroundColorRes = getColor(
                R.styleable.FTLExtendableTextView_backgroundColorRes,
                ContextCompat.getColor(context, R.color.AdditionalDarkBlue)
            )
            imageColorRes = getColor(
                R.styleable.FTLExtendableTextView_imageColorRes,
                ContextCompat.getColor(context, R.color.BackgroundPrimary)
            )
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

    private fun View.updateMargins(setMargin: Boolean) {
        val lParams = layoutParams as LayoutParams
        val marginTop = context.dpToPx(if (setMargin) 4f else 0f).toInt()
        lParams.updateMargins(
            marginStart,
            marginTop,
            marginLeft,
            marginBottom
        )
        layoutParams = lParams
    }

    override fun performClick(): Boolean {
        setExpand(!isExpandText)
        return super.performClick()
    }

    override fun onDraw(canvas: Canvas) {
        tvTextSlot.updateMargins(tvTextSlot.lineCount == 1)
        setExpand(isExpandText)
    }
}